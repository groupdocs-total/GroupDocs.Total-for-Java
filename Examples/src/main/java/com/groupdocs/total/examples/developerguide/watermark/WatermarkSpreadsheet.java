package com.groupdocs.total.examples.developerguide.watermark;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.watermark.Watermarker;
import com.groupdocs.watermark.watermarks.Font;
import com.groupdocs.watermark.watermarks.FontStyle;
import com.groupdocs.watermark.watermarks.TextWatermark;

public class WatermarkSpreadsheet {
    public static void run() throws Exception {
        try (Watermarker watermarker = new Watermarker(ExampleFiles.path("rate-card.xlsx"))) {
            TextWatermark watermark = new TextWatermark(
                "DRAFT", new Font("Calibri", 36, FontStyle.Bold));

            watermark.setOpacity(0.3);
            watermark.setRotateAngle(-30);

            watermarker.add(watermark);
            watermarker.save(ExampleFiles.output("watermark-spreadsheet.xlsx"));
        }
    }
}
