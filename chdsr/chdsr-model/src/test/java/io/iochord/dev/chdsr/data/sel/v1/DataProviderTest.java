package io.iochord.dev.chdsr.data.sel.v1;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import io.iochord.dev.chdsr.data.sel.v1.impl.EventImpl;
import io.iochord.dev.chdsr.data.sel.v1.impl.EventImplRepository;
import lombok.Getter;

/**
 *
 * @package chdsr-model
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 *
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = ContextConfiguration.class)
public class DataProviderTest {

	@Autowired
	@Getter
	private EventImplRepository eventRepository;

	public static List<String[]> getCsvData() throws Exception {
		File file = new File("Y:/9Repositories/SHI_SAMPLE_DATA_MASKED.csv");
		FileReader filereader = new FileReader(file);
		CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).withCSVParser(parser).build();
		List<String[]> allData = csvReader.readAll();
		return allData;
	}

	//@Test
	public void test01Insertion() throws Exception {
		List<String[]> allData = getCsvData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i = 0;
		int im = 1000000;
		int tick = im / 100;
		long started = System.currentTimeMillis();
		List<EventImpl> el = new ArrayList<>();
		while (i < im) {
			for (String[] row : allData) {
				String ec = row[1] + "|" + row[2];
				String ea = row[10];
				Date et = sdf.parse(row[11]);
				String eo = row[8];
				String er = row[8];
				EventImpl e = new EventImpl(ec, ea, et, eo, er);
				if (el.size() < 10000) {
					el.add(e);
				} else {
					getEventRepository().saveAll(el);
					el.clear();
				}
				i++;
				if (i % tick == 0) {
					System.out.println(i + " events saved: " + (System.currentTimeMillis() - started) + " ms");
				}
				if (i > im) {
					break;
				}
			}
		}
	}

	//@Test
	public void test02Selection() throws Exception {
		Sort s = new Sort(Sort.Direction.ASC, "caseId");
		s.and(new Sort(Sort.Direction.ASC, "timestamp"));
		Iterable<EventImpl> it = getEventRepository().findAll(s);
		Map<String, Map<String, Integer>> test = new LinkedHashMap<>();
		EventImpl pe = null;
		long started = System.currentTimeMillis();
		int ec = 0;
		for (EventImpl e : it) {
			if (pe != null) {
				if (pe.getCaseId().equalsIgnoreCase(e.getCaseId())) {
					String afrom = pe.getActivity();
					String ato = e.getActivity();
					if (!test.containsKey(afrom)) {
						test.put(afrom, new LinkedHashMap<>());
					}
					if (!test.get(afrom).containsKey(ato)) {
						test.get(afrom).put(ato, 0);
					}
					test.get(afrom).put(ato, test.get(afrom).get(ato) + 1);
				}
			}
			pe = e;
			ec++;
		}
		long ended = System.currentTimeMillis();
		for (String af : test.keySet()) {
			for (String at : test.get(af).keySet()) {
				double freq = test.get(af).get(at);
				double rfreq = 0;
				if (test.containsKey(at) && test.get(at).containsKey(af)) {
					rfreq = test.get(at).get(af);
				}
				double dep = freq - rfreq / (freq + rfreq + 1);
				System.out.print(af + " -> " + at + " : " + freq + " " + dep + " | ");
			}
		}
		System.out.println();
		System.out.println("DF Mining Finished: " + ec + " events: " + (ended - started) + " ms");
	}
	

}
