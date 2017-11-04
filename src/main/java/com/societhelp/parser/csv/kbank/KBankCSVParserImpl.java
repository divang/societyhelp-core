package com.societhelp.parser.csv.kbank;

import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by dsharma on 11/4/17.
 */

public class KBankCSVParserImpl {

	public static void main(String[] args) {
		KBankCSVParserImpl p = new KBankCSVParserImpl();

		List<KBankStatementPojo> t = (List<KBankStatementPojo>) p
				.getAllTransaction("src/main/resources/kbank-statement.csv");

		double creditedAmt = 0;
		double debitedAmt = 0;

		for (KBankStatementPojo ksp : t) {
			if (ksp.isCredit())
				creditedAmt += ksp.getAmount();
			else
				debitedAmt += ksp.getAmount();
			System.out.println(ksp.getName() + " " + ksp.getDescription());
		}

		System.out.println(creditedAmt + " creditedAmt ");
		System.out.println(debitedAmt + " debitedAmt");
	}

	// CSV file header
	private static final String[] FILE_HEADER_MAPPING = { "Sl. No.", "Date", "Description", "Chq / Ref number",
			"Amount", "Dr / Cr", "Balance" };

	private static final String[] SKIP_TRANSACTION_MAPPING = { "Sweep Trf", "SWEEP TRANSFER", "MATURITY PROCEEDS" };

	// Header values
	private static final String SL_NO = "Sl. No.";
	private static final String Date = "Date";
	private static final String Description = "Description";
	private static final String Ref_Number = "Chq / Ref number";
	private static final String Amount = "Amount";
	private static final String TYPE = "Dr / Cr";
	private static final String BALANCE = "Balance";

	private static final String COLUMN_SEPERATOR = ",";
	private static final NumberFormat NUM_FORMATTER = NumberFormat.getInstance(Locale.US);

	private boolean isHeaderFound(String line) {
		if (line.contains(SL_NO))
			return true;
		return false;
	}

	private boolean isTransaction(String line) {
		try {
			Long.parseUnsignedLong(line.split(COLUMN_SEPERATOR)[0]);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean isSkipTransaction(String line) {

		for (String sweepStr : SKIP_TRANSACTION_MAPPING) {
			if (line.contains(sweepStr))
				return true;
		}

		return false;
	}

	public Object getAllTransaction(String csvPath) {

		// Create a new list of student to be filled by CSV file data
		List<KBankStatementPojo> transactions = new ArrayList();

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		// Create the CSVFormat object with the header mapping
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		try {

			// initialize FileReader object
			fileReader = new FileReader(csvPath);

			// initialize CSVParser object
			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			// Get a list of CSV file records
			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			boolean headerFound = false;
			for (CSVRecord record : csvRecords) {
				try {
					if (!headerFound) {
						headerFound = isHeaderFound(record.get(0));
						continue;
					}

					if (isTransaction(record.get(0))) {

						if (!isSkipTransaction(record.get(Description))) {
							KBankStatementPojo transaction = new KBankStatementPojo(Integer.parseInt(record.get(SL_NO)),
									record.get(Date), record.get(Description), record.get(Ref_Number),
									NUM_FORMATTER.parse(record.get(Amount)).doubleValue(), record.get(TYPE),
									NUM_FORMATTER.parse(record.get(BALANCE)).doubleValue());
							transactions.add(transaction);
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileReader !!!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
				csvFileParser.close();
			} catch (Exception e) {
				System.out.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();
			}
		}
		return transactions;
	}
}