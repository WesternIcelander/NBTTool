package io.siggi.nbt.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.siggi.nbt.NBTCompound;
import io.siggi.nbt.NBTTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
			write(new File(args[1]), read(new File(args[0])));
		} else {
			printHelp();
		}
	}

	private static void printHelp() {
		System.err.println("Usage:");
		System.err.println("    Print out NBT as JSON");
		System.err.println("        nbttool [input]");
		System.err.println("    Convert NBT from one format to another");
		System.err.println("        nbttool [input] [output]");
		System.err.println();
		System.err.println("The format is determined from the file extension.");
		System.err.println("Supported extensions:");
		System.err.println("    *.json  - NBT in NBTTool's JSON format");
		System.err.println("    *.nbt   - Binary NBT data");
		System.err.println("    *.dat   - Binary NBT data compressed with gzip");
	}

	private static NBTCompound read(File f) throws IOException {
		try (FileInputStream in = new FileInputStream(f)) {
			String lowerName = f.getName().toLowerCase();
			if (lowerName.endsWith(".nbt")) {
				return NBTTool.deserialize(in);
			} else if (lowerName.endsWith(".dat")) {
				try (GZIPInputStream gIn = new GZIPInputStream(in)) {
					return NBTTool.deserialize(gIn);
				}
			} else if (lowerName.endsWith(".json")) {
				try (Reader reader = new InputStreamReader(in)) {
					return gson.fromJson(reader, NBTCompound.class);
				}
			} else {
				return null;
			}
		}
	}

	private static void write(File f, NBTCompound compound) throws IOException {
		try (FileOutputStream out = new FileOutputStream(f)) {
			String lowerName = f.getName().toLowerCase();
			if (lowerName.endsWith(".nbt")) {
				NBTTool.serialize(out, compound);
			} else if (lowerName.endsWith(".dat")) {
				try (GZIPOutputStream gOut = new GZIPOutputStream(out)) {
					NBTTool.serialize(gOut, compound);
				}
			} else if (lowerName.endsWith(".json")) {
				out.write(gson.toJson(compound).getBytes(StandardCharsets.UTF_8));
			}
		}
	}
}
