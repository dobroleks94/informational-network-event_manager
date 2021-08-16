package com.dbdiploma.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.Color;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataSetDTO {

    private String label;
    private String fill;
    private List<Long> data;
    private String borderColor;
    private String pointBackgroundColor;
    private String pointHoverBackgroundColor;
    private String pointBorderColor;
    private String backgroundColor;
    private double borderWidth;
    private int pointRadius;
    private int pointHoverRadius;

    public DataSetDTO(String hostName, List<Long> data){
        this.data = data;
        this.label = hostName;

        this.fill = "start";
        this.borderWidth = 1.5;
        this.pointRadius = 0;
        this.pointHoverRadius = 3;
        this.pointBackgroundColor = "#ffffff";

        Color color = getRandomColor();
        this.borderColor = String.format("rgba(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
        this.pointHoverBackgroundColor = String.format("rgba(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
        this.pointBorderColor = String.format("rgba(%d,%d,%d)", color.getRed(), color.getGreen(), color.getBlue());
        this.backgroundColor = String.format("rgba(%d,%d,%d,%f)", color.getRed(), color.getGreen(), color.getBlue(), 0.1);

    }

    private Color getRandomColor() {
        Color color = new Color(
                Math.round(Math.random()),
                Math.round(Math.random()),
                Math.round(Math.random()));
        if (color.getRGB() == Color.WHITE.getRGB())
            color = getRandomColor();
        return color;
    }
}
