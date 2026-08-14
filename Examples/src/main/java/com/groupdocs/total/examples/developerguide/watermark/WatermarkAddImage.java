package com.groupdocs.total.examples.developerguide.watermark;

import com.groupdocs.total.examples.ExampleFiles;
import com.groupdocs.watermark.Watermarker;
import com.groupdocs.watermark.common.HorizontalAlignment;
import com.groupdocs.watermark.common.VerticalAlignment;
import com.groupdocs.watermark.watermarks.ImageWatermark;

public class WatermarkAddImage {
    public static void run() throws Exception {
        try (Watermarker watermarker = new Watermarker(ExampleFiles.path("contract.pdf"))) {
            try (ImageWatermark watermark = new ImageWatermark(ExampleFiles.path("logo.png"))) {
                watermark.setOpacity(0.5);
                watermark.setHorizontalAlignment(HorizontalAlignment.Center);
                watermark.setVerticalAlignment(VerticalAlignment.Center);

                watermarker.add(watermark);
            }

            watermarker.save(ExampleFiles.output("watermark-add-image.pdf"));
        }
    }
}
