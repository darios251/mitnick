package controllers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import models.OutputResult;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.Controller;
import play.mvc.With;
import util.DATConstants;
import util.OutputDTO;
import controllers.CRUD.ObjectType;

/**
 * This CRUD allow to export the results into a xls file.
 * 
 * @author Martin
 * 
 */
@Check(DATConstants.Constants.Roles.DOMIANS_ROLE)
@With(Secure.class)
public class OutputResultGenerator extends Controller {

	private static WritableCellFormat timesBoldUnderline;
	private static WritableCellFormat times;

	public static void listResult(String orderBy, String order, boolean delete) {
		OutputResult output = OutputResult.getInstance();
		String inGoogle =null;
		String www = null;
		String pr = null;
		String refDomians = null;
		String refips = null;
		String refsubnet = null;
		String extlinksedu = null;
		String refdomainedu = null;
		String extbacklinksgov = null;
		String refdomiansgov = null;
		String refdomainshome = null;
		String percenttoh = null;
		if (delete){
			inGoogle = output.getInGoogle();
			www = output.getWww();
			pr = output.getPr();
			refDomians = output.getRefDomians();
			refips = output.getRefips();
			refsubnet = output.getRefsubnet();
			extlinksedu = output.getExtlinksedu();
			refdomainedu = output.getRefdomainedu();
			extbacklinksgov = output.getExtbacklinksgov();
			refdomiansgov = output.getRefdomiansgov();
			refdomainshome = output.getRefdomainshome();
			percenttoh = output.getPercenttoh();
		} else {
			inGoogle = params.get("inGoogle");
			www = params.get("www");
			pr = params.get("pr");
			refDomians = params.get("refDomians");
			refips = params.get("refips");
			refsubnet = params.get("refsubnet");
			extlinksedu = params.get("extlinksedu");
			refdomainedu = params.get("refdomainedu");
			extbacklinksgov = params.get("extbacklinksgov");
			refdomiansgov = params.get("refdomiansgov");
			refdomainshome = params.get("refdomainshome");
			percenttoh = params.get("percenttoh");
			
			output.setInGoogle(inGoogle);
			output.setWww(www);
			output.setPr(pr);
			output.setRefDomians(refDomians);
			output.setRefips(refips);
			output.setRefsubnet(refsubnet);
			output.setExtlinksedu(extlinksedu);
			output.setRefdomainedu(refdomainedu);
			output.setExtbacklinksgov(extbacklinksgov);
			output.setRefdomiansgov(refdomiansgov);
			output.setRefdomainshome(refdomainshome);
			output.setPercenttoh(percenttoh);
		}
		
		List<OutputDTO> results = output.getResult();
		if (orderBy==null)
			orderBy = "site";
		if ("DESC".equals(order))
			orderDESC(results, orderBy);
		else
			orderASC(results, orderBy);
		render(results, orderBy, order, inGoogle, www, pr, refDomians, refips, refsubnet, extlinksedu, refdomainedu, extbacklinksgov, refdomiansgov, refdomainshome, percenttoh);
	}
	
	public static void delete(String orderBy, String order, String site) {			
		OutputResult outputs = OutputResult.getInstance();
		List<OutputDTO> results = outputs.getResult();
		OutputDTO remove = null;
		for (OutputDTO output: results){
			if (site!=null && site.equals(output.getSite()))
				remove = output;
		}
		if (remove!=null)
			outputs.getResult().remove(remove);
		
		listResult(orderBy, order, true);
	}

	public static void goodToBuy(String site){
		OutputResult outputs = OutputResult.getInstance();
		List<OutputDTO> results = outputs.getResult();
		for (OutputDTO output: results){
			if (site!=null && site.equals(output.getSite()))
				output.setGoodToBuy(!output.isGoodToBuy());
		}
	}
	
	public static void createXLSFile() throws Exception {
		ObjectType type = ObjectType.get(getControllerClass());
		notFoundIfNull(type);
		Constructor<?> constructor = type.entityClass.getDeclaredConstructor();
		constructor.setAccessible(true);
		Model object = (Model) constructor.newInstance();
		Binder.bindBean(params.getRootParamNode(), "object", object);

		validation.valid(object);
		if (validation.hasErrors()) {
			renderArgs.put("error", play.i18n.Messages.get("crud.hasErrors"));
			try {
				render(request.controller.replace(".", "/") + "/blank.html", type, object);
			} catch (TemplateNotFoundException e) {
				render("CRUD/blank.html", type, object);
			}
		}

		write(((OutputResult) object).fileName);
		object._save();

		flash.success(play.i18n.Messages.get("output.process.success", ((OutputResult) object).fileName));
		if (params.get("_save") != null) {
			redirect("Admin.index");
		}

		redirect("Admin.index");
	}

	private static void write(String inputFile) throws IOException, WriteException {
		File file = new File(inputFile + ".xls");
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet(play.i18n.Messages.get("output.tab.label"), 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createLabel(excelSheet);
		createContent(excelSheet);

		workbook.write();
		workbook.close();

		// abrir el archivo
		Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + inputFile + ".xls");
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
		addCaption(sheet, 14, 0, play.i18n.Messages.get("output.header.goodToBuy"));

	}

	private static void createContent(WritableSheet sheet) throws WriteException, RowsExceededException {
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
			addLabel(sheet, 14, i, String.valueOf(output.isGoodToBuy()));
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

	@SuppressWarnings("unchecked")
	private static OutputDTO orderDESC(List<OutputDTO> result, String orderBy) {
		if ("site".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getSite().compareTo(e1.getSite());
				}
			});
		}
		if ("pr".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getPr().compareTo(e1.getPr());
				}
			});
		}
		if ("ingoogle".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getInGoogle().compareTo(e1.getInGoogle());
				}
			});
		}
		if ("www".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getWww().compareTo(e1.getWww());
				}
			});
		}
		if ("refdomain".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomians().compareTo(e1.getRefDomians());
				}
			});
		}
		if ("refips".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefIps().compareTo(e1.getRefIps());
				}
			});
		}
		if ("refsubnet".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefSubNet().compareTo(e1.getRefSubNet());
				}
			});
		}
		if ("extlinksedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getExtBackLnksEdu().compareTo(e1.getExtBackLnksEdu());
				}
			});
		}
		if ("refdomainedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomainsEdu().compareTo(e1.getRefDomainsEdu());
				}
			});
		}
		if ("extbacklinksgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getExtBackLinksGov().compareTo(e1.getExtBackLinksGov());
				}
			});
		}
		if ("refdomiansgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomainsGov().compareTo(e1.getRefDomainsGov());
				}
			});
		}
		if ("refdomainshome".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getRefDomianHome().compareTo(e1.getRefDomianHome());
				}
			});
		}
		if ("percenttoh".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e2.getPercentToHome().compareTo(e1.getPercentToHome());
				}
			});
		}
		return result.get(0);
	}
	
	@SuppressWarnings("unchecked")
	private static OutputDTO orderASC(List<OutputDTO> result, String orderBy) {
		if ("site".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getSite().compareTo(e2.getSite());
				}
			});
		}
		if ("pr".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getPr().compareTo(e2.getPr());
				}
			});
		}
		if ("ingoogle".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getInGoogle().compareTo(e2.getInGoogle());
				}
			});
		}
		if ("www".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getWww().compareTo(e2.getWww());
				}
			});
		}
		if ("refdomain".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomians().compareTo(e2.getRefDomians());
				}
			});
		}
		if ("refips".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefIps().compareTo(e2.getRefIps());
				}
			});
		}
		if ("refsubnet".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefSubNet().compareTo(e2.getRefSubNet());
				}
			});
		}
		if ("extlinksedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getExtBackLnksEdu().compareTo(e2.getExtBackLnksEdu());
				}
			});
		}
		if ("refdomainedu".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomainsEdu().compareTo(e2.getRefDomainsEdu());
				}
			});
		}
		if ("extbacklinksgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getExtBackLinksGov().compareTo(e2.getExtBackLinksGov());
				}
			});
		}
		if ("refdomiansgov".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomainsGov().compareTo(e2.getRefDomainsGov());
				}
			});
		}
		if ("refdomainshome".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getRefDomianHome().compareTo(e2.getRefDomianHome());
				}
			});
		}
		if ("percenttoh".equals(orderBy)) {
			Collections.sort(result, new Comparator() {
				public int compare(Object o1, Object o2) {
					OutputDTO e1 = (OutputDTO) o1;
					OutputDTO e2 = (OutputDTO) o2;
					return e1.getPercentToHome().compareTo(e2.getPercentToHome());
				}
			});
		}
		return result.get(0);
	}

}