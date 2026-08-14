package com.groupdocs.total.examples.developerguide.watermark;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.watermark.Watermarker;
import com.groupdocs.watermark.common.HorizontalAlignment;
import com.groupdocs.watermark.common.VerticalAlignment;
import com.groupdocs.watermark.watermarks.Color;
import com.groupdocs.watermark.watermarks.Font;
import com.groupdocs.watermark.watermarks.FontStyle;
import com.groupdocs.watermark.watermarks.TextWatermark;

public class WatermarkAddText {
    public static void run() throws Exception {
        try (Watermarker watermarker = new Watermarker(ExampleFiles.path("contract.docx"))) {
            TextWatermark watermark = new TextWatermark(
                "CONFIDENTIAL", new Font("Arial", 42, FontStyle.Bold));

            watermark.setForegroundColor(Color.getRed());
            watermark.setOpacity(0.4);
            watermark.setRotateAngle(-45);
            watermark.setHorizontalAlignment(HorizontalAlignment.Center);
            watermark.setVerticalAlignment(VerticalAlignment.Center);

            watermarker.add(watermark);
            watermarker.save(ExampleFiles.output("watermark-add-text.docx"));
        }
    }
}
