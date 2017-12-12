package com.ncut.movielens;

import com.google.common.base.Charsets;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.common.iterator.FileLineIterable;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by Zhou Ning on 2017/12/12.
 * <p>
 * Desc:
 */
public class MovieLensDataModel extends FileDataModel {
    private static String COLON_DELIMITER = "::";
    private static Pattern COLON_DELIMITER_PATTER = Pattern.compile(COLON_DELIMITER);

    public MovieLensDataModel(File dataFile) throws IOException {
        super(convertFile(dataFile));
    }

    private static File convertFile(File originalFile) throws IOException {
        File resultFile = new File(System.getProperty("java.io.tmpdir"), "ratings.csv");
        if (resultFile.exists()) {
            resultFile.delete();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(resultFile), Charsets.UTF_8)) {
            for (String line : new FileLineIterable(originalFile, false)) {
                int lastIndex = line.lastIndexOf(COLON_DELIMITER);

                if (lastIndex < 0) {
                    System.out.println("Invalid data! data: " + line);
                    continue;
                }

                String subLine = line.substring(0, lastIndex);

                String convertedSubLine = COLON_DELIMITER_PATTER.matcher(subLine).replaceAll(",");
                writer.write(convertedSubLine);
                writer.write("\n");
            }
        } catch (IOException e) {
            resultFile.delete();
            throw e;
        }
        return resultFile;
    }
}