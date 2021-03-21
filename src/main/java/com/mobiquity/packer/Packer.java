package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.model.InputLine;
import com.mobiquity.packer.model.Item;
import com.mobiquity.packer.utils.Common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        if (filePath == null) {
            throw new APIException("filePath is empty!");
        }

        Path path = Paths.get(filePath);
        try {

            List<String> lines = Files.lines(path).collect(Collectors.toList());

            StringBuffer sb = new StringBuffer();

            for (String line : lines) {
                sb.append(Packer.parse(line + "\n"));
            }

            return sb.toString();

        } catch (IOException e) {
            throw new APIException("File not found: " + e.getMessage());
        }


    }


    public static String parse(String line) throws APIException {

        InputLine inputLine = null;


        String[] lineArr = line.split(" : ");

        try {

            if (lineArr.length == 2) {

                Integer weightLimit = Integer.parseInt(lineArr[0]);

                String[] itemsArr = lineArr[1].split("\\s+");

                if (itemsArr.length > 0) {

                    List<Item> itemsList = new ArrayList<>();

                    for (String itemString : itemsArr) {

                        String[] itemParts = itemString.replace("(", "").replace(")", "").split(",");
                        if (itemParts.length == 3) {

                            itemsList.add(new Item(Integer.parseInt(itemParts[0]),
                                    Double.parseDouble(itemParts[1]),
                                    Integer.parseInt(itemParts[2].replace("€", ""))
                            ));

                        } else {
                            itemsList.clear();
                            break;
                        }
                    }

                    if (!itemsList.isEmpty()) {
                        inputLine = new InputLine(weightLimit, itemsList);
                    } else {
                        throw new APIException("Invalid input file");
                    }

                }

            }
        } catch (NumberFormatException e) {
            throw new APIException("Invalid input Format :" + e.getMessage());
        }


        List<Item> items = Common.findPack(inputLine.getWeightLimit(), inputLine.getItems());

        String res;

        if (items != null && !items.isEmpty()) {
            res = items.stream().map(i -> i.getIndex().toString()).collect(Collectors.joining(","));
        } else {
            res = "-";
        }

        return res;
    }


}
