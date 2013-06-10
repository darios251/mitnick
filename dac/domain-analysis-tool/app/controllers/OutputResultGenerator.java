package controllers;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import models.Domains;
import play.mvc.With;
import util.DATConstants;
import util.OutputDTO;

import jxl.write.Number;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
@CRUD.For(value = Domains.class)
public class OutputResultGenerator extends CRUD {

	private static WritableCellFormat timesBoldUnderline;
	private static WritableCellFormat times;

	/**
	 * El metodo list tiene la funcionalidad del blank porque nunca se enlistan los archivos procesados.
	 * @param page
	 * @param search
	 * @param searchFields
	 * @param orderBy
	 * @param order
	 */
	public static void list(int page, String search, String searchFields, String orderBy, String order) {
		try {
			write("c:/agustina/lars1.xls");
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Please check the result file under c:/temp/lars.xls ");
	}
	

	public static void write(String inputFile) throws IOException, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet(play.i18n.Messages.get("output.tab.label"), 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);

		workbook.write();
		workbook.close();
	}

	private static void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false, UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write headers
		addCaption(sheet, 0, 0, play.i18n.Messages.get("output.header.site"));
		addCaption(sheet, 1, 0, play.i18n.Messages.get("output.header.pr"));
		addCaption(sheet, 2, 0, play.i18n.Messages.get("output.header.inGoogle"));
		addCaption(sheet, 3, 0, play.i18n.Messages.get("output.header.www"));
		addCaption(sheet, 4, 0, play.i18n.Messages.get("output.header.percentToHome"));
		addCaption(sheet, 5, 0, play.i18n.Messages.get("output.header.refDomianHome"));
		addCaption(sheet, 6, 0, play.i18n.Messages.get("output.header.refDomians"));
		addCaption(sheet, 7, 0, play.i18n.Messages.get("output.header.extBackLinks"));
		addCaption(sheet, 8, 0, play.i18n.Messages.get("output.header.refIps"));
		addCaption(sheet, 9, 0, play.i18n.Messages.get("output.header.refSubNet"));
		addCaption(sheet, 10, 0, play.i18n.Messages.get("output.header.extBackLnksEdu"));
		addCaption(sheet, 11, 0, play.i18n.Messages.get("output.header.extBackLinksGov"));
		addCaption(sheet, 12, 0, play.i18n.Messages.get("output.header.refDomainsEdu"));
		addCaption(sheet, 13, 0, play.i18n.Messages.get("output.header.refDomainsGov"));

	}

	private static void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
		// Write a few number
		List<OutputDTO> outputs = OutputDTO.getPruebas();
		int i = 1;
		for (OutputDTO output : outputs) {
			addLabel(sheet, 0, i, output.getSite());
			addNumber(sheet, 1, i, output.getPr());
			addLabel(sheet, 2, i, output.getInGoogle());
			addLabel(sheet, 3, i, output.getWww());
			addLabel(sheet, 4, i, output.getPercentToHome().toString().concat("%"));
			addNumber(sheet, 5, i, output.getRefDomianHome());
			addNumber(sheet, 6, i, output.getRefDomians());
			addNumber(sheet, 7, i, output.getExtBackLinks());
			addNumber(sheet, 8, i, output.getRefIps());
			addNumber(sheet, 9, i, output.getRefSubNet());
			addNumber(sheet, 10, i, output.getExtBackLnksEdu());
			addNumber(sheet, 11, i, output.getExtBackLinksGov());
			addNumber(sheet, 12, i, output.getRefDomainsEdu());
			addNumber(sheet, 13, i, output.getRefDomainsGov());
			i++;
		}
		
	}

	private static void addCaption(WritableSheet sheet, int column, int row, String s) throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private static void addNumber(WritableSheet sheet, int column, int row, BigDecimal integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer.intValue(), times);
		sheet.addCell(number);
	}

	private static void addLabel(WritableSheet sheet, int column, int row, String s) throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	
}