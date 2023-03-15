package events;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Object.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import java.net.MalformedURLException;

import java.util.LinkedHashMap;


public class ExportToPdf {

    public static void exportToPdf() throws FileNotFoundException {

        try{
            Interaction interaction = Interaction.getInteractionInstance();
            // creating the pdf document for export
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter("Statistics.pdf"));
            Document doc = new Document(pdfDocument);
            // creating the table of the popular meals

            Table table=new Table(3);
            table.addCell(new Cell().add(new Paragraph("Sort Number")));
            table.addCell(new Cell().add(new Paragraph("Meal Name")));
            table.addCell(new Cell().add(new Paragraph("Popularity")));
            // connecting to the DB to get the list of 10 most popular meals ih desc order

            LinkedHashMap<String,Integer> statistics = interaction.mostViewedMeals();

            // add all the content to the table of the pdf

            statistics.forEach((key, value) -> {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(table.getNumberOfRows())).setTextAlignment(TextAlignment.CENTER)));
                table.addCell(new Cell().add(new Paragraph(key)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(value)).setTextAlignment(TextAlignment.CENTER)));
            });

            // create image logo for the pdf

            ImageData imageData = null;
            try {
                imageData = ImageDataFactory.create("src/media/logoMealsApp.png");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            // create chart for the pdf

            BufferedImage chart = generatePieChart(statistics).createBufferedImage(600,600);
            ImageData imageChartData = null;
            try {
                imageChartData = ImageDataFactory.create(chart,null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // add all the above things to the pdf document and present them nice (resize, align , fonts , etc)

            Image logo = new Image(imageData);
            Image imageChart = new Image(imageChartData);
            logo.scaleAbsolute(150,150);
            imageChart.scaleAbsolute(600,600);
            logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
            imageChart.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(logo);
            doc.add(new Paragraph("The top 10 of Favorites").setFontSize(12).setBold().setTextAlignment(TextAlignment.CENTER));
            table.setFontSize(12);
            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(table);
            doc.add(imageChart);
            doc.close();
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    // method to create a chart for the 10 top meals
    public static JFreeChart generatePieChart(LinkedHashMap<String,Integer> statistics) {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        statistics.forEach((key, value) -> {
            dataSet.setValue(key.toString(), value.intValue());
        });

        JFreeChart chart = ChartFactory.createPieChart("The top 10 of Favorites", dataSet, true, true, false);
        return chart;
    }
}
