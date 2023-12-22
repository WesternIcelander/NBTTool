package io.siggi.nbt.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTTool;
import io.siggi.nbt.NBTType;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTToolUtility {
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        NBTTool.registerTo(builder);
        gson = builder.create();
    }

    private static Gson gson;

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            if (args[0].equals("-h") || args[0].equals("--help") || args[0].equals("-?")) {
                printHelp();
                return;
            }
            System.out.println(gson.toJson(read(new File(args[0]))));
        } else if (args.length == 2) {
			write(new File(args[1]), read(new File(args[0])), null);
		} else if (args.length == 3) {
			NBTFileFormat format;
			switch (args[2]) {
				case "raw":
				case "nbt":
					format = NBTFileFormat.RAW;
					break;
				case "gzip":
					format = NBTFileFormat.GZIP;
					break;
				case "json":
					format = NBTFileFormat.JSON;
					break;
				default:
					printHelp();
					return;
			}
			write(new File(args[1]), read(new File(args[0])), format);
		} else {
            printHelp();
        }
    }

    private static void printHelp() {
        System.err.println("Usage:");
        System.err.println("    Print out NBT as JSON");
        System.err.println("        nbttool <input>");
        System.err.println("    Convert NBT from one format to another");
		System.err.println("        nbttool <input> <output> [output format]");
		System.err.println("            supported formats:");
		System.err.println("                - raw");
		System.err.println("                - gzip");
		System.err.println("                - json");
		System.err.println("            if the output format is not specified, it will be guessed based");
		System.err.println("            on the file name, extension, and file contents.");
        System.err.println();
    }

    private static NBTCompound read(File f) throws IOException {
        switch (detectFormat(f)) {
            case RAW:
                try (FileInputStream in = new FileInputStream(f)) {
                    return NBTTool.deserialize(in);
                }
            case GZIP:
                try (FileInputStream in = new FileInputStream(f)) {
                    try (GZIPInputStream gIn = new GZIPInputStream(in)) {
                        return NBTTool.deserialize(gIn);
                    }
                }
            case JSON:
                try (FileReader reader = new FileReader(f)) {
                    return gson.fromJson(reader, NBTCompound.class);
                }
            default:
                throw new IOException("Unknown format: " + f.getName());
        }
    }

    private static NBTFileFormat detectFormat(File f) throws IOException {
        try (FileInputStream in = new FileInputStream(f)) {
            int firstByte = in.read();
            int secondByte = in.read();
            if (firstByte == -1 || secondByte == -1) {
                throw new EOFException("File too short");
            }
            if (firstByte == 0x1f && secondByte == 0x8b) {
                return NBTFileFormat.GZIP;
            } else if (firstByte < NBTType.values().length && secondByte < 2) {
                // This will only work if the root tag has a name less than 512 bytes.
                return NBTFileFormat.RAW;
            } else {
                return NBTFileFormat.JSON;
            }
        }
    }

    private static void write(File f, NBTCompound compound, NBTFileFormat format) throws IOException {
		format:
        if (format == null) {
            String name = f.getName();
			switch (name) {
				case "servers.dat":
					format = NBTFileFormat.RAW;
					break format;
				case "level.dat":
					// Bedrock Edition level.dat is raw, while Java Edition level.dat is gzipped.
					// Only Bedrock contains baseGameVersion so we can choose the appropriate format based on its presence.
					String string = compound.getString("baseGameVersion");
					if (string != null && !string.isEmpty()) {
						format = NBTFileFormat.RAW;
						break format;
					}
			}
            int dotPosition = name.lastIndexOf(".");
            if (dotPosition == -1) {
                format = NBTFileFormat.RAW;
            } else {
                String extension = name.substring(dotPosition + 1).toLowerCase();
                switch (extension) {
                    case "dat":
                        format = NBTFileFormat.GZIP;
                        break;
                    case "json":
                        format = NBTFileFormat.JSON;
                        break;
                    case "nbt":
                    default:
                        format = NBTFileFormat.RAW;
                        break;
                }
            }
        }
        switch (format) {
            case RAW:
                try (FileOutputStream out = new FileOutputStream(f)) {
                    NBTTool.serialize(out, compound);
                }
                break;
            case GZIP:
                try (FileOutputStream out = new FileOutputStream(f)) {
                    try (GZIPOutputStream gOut = new GZIPOutputStream(out)) {
                        NBTTool.serialize(gOut, compound);
                    }
                }
                break;
            case JSON:
                try (FileWriter writer = new FileWriter(f)) {
                    gson.toJson(compound, writer);
                }
                break;
        }
    }
}
