package cn.nuaa.ai.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BWTCodeLines {
	private static List<SourceCode> codeSnippets = new ArrayList<SourceCode>();
	private static List<List<TokenList>> FirstLastRow = new ArrayList<List<TokenList>>();
	private static File[] insFiles;
	private static Map<String, Map<String, Double>> seeds = new HashMap<String, Map<String, Double>>();

	public static void main(String[] args) {
		runningDemo2();
		
		//readData("Activiti-develop@AbstractActivitiTestCase#assertAndEnsureCleanDb.txt");
	}

	/**
	 * ����demo;
	 */
	public static void runningDemo() {
		long startTime = System.currentTimeMillis();// ��¼��ȡ���ݿ�ʼʱ��;
		readCode("F:\\data\\github\\methodFormatBody\\");
		System.out.println("read in process finished");
		long endTime = System.currentTimeMillis();// ��¼��ȡ���ݽ���ʱ��;
		System.out.println("read in time��" + 1.0 * (endTime - startTime) / 1000 + "s");
		//��ʼ�Ƽ�����;
		while (true) {
			startTime = System.currentTimeMillis();// ��¼��ѯ��ʼʱ��;

			List<TokenList> linecode = readCodeFromFile("F:\\data\\github\\methodFormatBody\\0.txt");
			SourceCode sc = new SourceCode();

			List<List<TokenList>> cutCode = SourceCodeCutting(linecode);
			if (cutCode.get(0).isEmpty() && cutCode.get(1).isEmpty()) {
				System.out.println("Seed code format error");
			}
			if (cutCode.get(0).isEmpty()) {
				sc.setCodes(cutCode.get(1));
				sc.setId(-1);
				sc.setName("back seed code");
				BWTSearch(sc);
				// System.out.println("1111111111111111111111111111111111");
			} else if (cutCode.get(1).isEmpty()) {
				sc.setCodes(cutCode.get(0));
				sc.setId(-1);
				sc.setName("front seed code");
				BWTSearch(sc);
				// System.out.println("2222222222222222222222222222222222");
			} else {
				SourceCode fsc = new SourceCode();
				fsc.setCodes(cutCode.get(0));
				fsc.setId(-1);
				fsc.setName("front seed code");

				SourceCode bsc = new SourceCode();
				bsc.setCodes(cutCode.get(1));
				bsc.setId(-1);
				bsc.setName("back seed code");

				List<Similarity2ClassIndex> simiList = new ArrayList<Similarity2ClassIndex>();
				for (int i = 0; i < codeSnippets.size(); i++) {
					SourceCode is = new SourceCode(codeSnippets.get(i));
					double s1 = getSimilarity(new SourceCode(fsc), is);
					double s2 = getSimilarity(new SourceCode(bsc), is);
					Similarity2ClassIndex s2c = new Similarity2ClassIndex();
					s2c.setClassId(i);
					if (Math.abs(s1 - s2) < 0.3) {
						s2c.setSimilarity(0.6 * s1 + 0.4 * s2);
					} else {
						s2c.setSimilarity(s1 > s2 ? s1 : s2);
					}
					simiList.add(s2c);

					FirstLastRow.clear();
				}
				Collections.sort(simiList);
				int i = 0;
				for (Similarity2ClassIndex s2c : simiList) {
					System.out.println(
							s2c.getClassId() + "  " + s2c.getSimilarity() + "   " + insFiles[s2c.getClassId()]);
					i++;
					if (i > 11) {
						break;
					}
				}
				// System.out.println("333333333333333333333333333333333333333333");
			}

			endTime = System.currentTimeMillis();// ��¼��ѯ����ʱ��;
			System.out.println("search time��" + 1.0 * (endTime - startTime) / 1000 + "s");

			@SuppressWarnings("resource")
			Scanner sb = new Scanner(System.in);
			System.out.print("Continue or Not: ");
			String com = sb.nextLine();
			if (com.equals("y") || com.equals("Y")) {
				continue;
			} else {
				break;
			}
		}
	}
	
	/**
	 * ����demo2;
	 */
	public static List<String> runningDemo2() {
		List<String> codeNames = new ArrayList<String>();
		List<String> codeContent = new ArrayList<String>();
		long startTime = System.currentTimeMillis();// ��¼��ȡ���ݿ�ʼʱ��;
		readCode("F:\\data\\github\\methodFormatBody\\");
		System.out.println("read in process finished");
		long endTime = System.currentTimeMillis();// ��¼��ȡ���ݽ���ʱ��;
		System.out.println("read in time��" + 1.0 * (endTime - startTime) / 1000 + "s");
		//��ʼ�Ƽ�����;

		startTime = System.currentTimeMillis();// ��¼��ѯ��ʼʱ��;

		List<TokenList> linecode = readCodeFromFile("F:\\Java\\TestPlugins\\code\\0.txt");
		SourceCode sc = new SourceCode();

		List<List<TokenList>> cutCode = SourceCodeCutting(linecode);
		if (cutCode.get(0).isEmpty() && cutCode.get(1).isEmpty()) {
			System.out.println("Seed code format error");
		}
		if (cutCode.get(0).isEmpty()) {
			sc.setCodes(cutCode.get(1));
			sc.setId(-1);
			sc.setName("back seed code");
			codeNames = BWTSearch(sc);
			System.out.println("1111111111111111111111111111111111");
		} else if (cutCode.get(1).isEmpty()) {
			sc.setCodes(cutCode.get(0));
			sc.setId(-1);
			sc.setName("front seed code");
			codeNames = BWTSearch(sc);
			System.out.println("2222222222222222222222222222222222");
		} else {
			SourceCode fsc = new SourceCode();
			fsc.setCodes(cutCode.get(0));
			fsc.setId(-1);
			fsc.setName("front seed code");

			SourceCode bsc = new SourceCode();
			bsc.setCodes(cutCode.get(1));
			bsc.setId(-1);
			bsc.setName("back seed code");

			List<Similarity2ClassIndex> simiList = new ArrayList<Similarity2ClassIndex>();
			for (int i = 0; i < codeSnippets.size(); i++) {
				SourceCode is = new SourceCode(codeSnippets.get(i));
				double s1 = getSimilarity(new SourceCode(fsc), is);
				double s2 = getSimilarity(new SourceCode(bsc), is);
				Similarity2ClassIndex s2c = new Similarity2ClassIndex();
				s2c.setClassId(i);
				if (Math.abs(s1 - s2) < 0.3) {
					s2c.setSimilarity(0.6 * s1 + 0.4 * s2);
				} else {
					s2c.setSimilarity(s1 > s2 ? s1 : s2);
				}
				simiList.add(s2c);

				FirstLastRow.clear();
			}
			Collections.sort(simiList);
			int i = 0;
			for (Similarity2ClassIndex s2c : simiList) {
				System.out.println(
						s2c.getClassId() + "  " + s2c.getSimilarity() + " " + insFiles[s2c.getClassId()]);
				System.out.println();
				codeNames.add(insFiles[s2c.getClassId()].getName());
				i++;
				if (i > 11) {
					break;
				}
			}
			System.out.println("333333333333333333333333333333333333333333");
		}

		endTime = System.currentTimeMillis();// ��¼��ѯ����ʱ��;
		System.out.println("search time��" + 1.0 * (endTime - startTime) / 1000 + "s");
		System.out.println("code snippets size��" + codeNames.size());
		
		for(int i = 0; i < codeNames.size();i++){
			System.out.println("F:\\data\\github\\methodbody\\" + codeNames.get(i));
			codeContent.add(getCodeFromFile("F:\\data\\github\\methodbody\\" + codeNames.get(i)));
		}
		return codeContent;
	}
	/**
	 * ����Ҫ��ѯ�Ĵ�������и�; ȡǰ5�кͺ�5��;
	 */
	public static List<List<TokenList>> SourceCodeCutting(List<TokenList> code) {

		// System.out.println(code.get(4).getTokens());

		List<List<TokenList>> cutCode = new ArrayList<List<TokenList>>();

		List<TokenList> frontCode = new ArrayList<TokenList>();
		List<TokenList> backCode = new ArrayList<TokenList>();
		int i = 0;
		for (i = 0; i < code.size(); i++) {
			if (code.get(i).getTokens().contains("mycursorposition")) {
				// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				break;
			}
		}
		int front = (i - 5) >= 0 ? (i - 5) : 0;
		int back = (i + 5) < code.size() ? (i + 5) : (code.size() - 1);

		// System.out.println(front + " " + back);

		for (int j = front; j < i; j++) {
			frontCode.add(code.get(j));
		}
		for (int j = i + 1; j <= back; j++) {
			backCode.add(code.get(j));
		}

		cutCode.add(frontCode);
		cutCode.add(backCode);

		return cutCode;
	}

	/**
	 * �ڼ���Ĺ����м���LC;
	 */
	public static List<String> BWTSearch(SourceCode seed) {
		List<String> codes = new ArrayList<String>();
		List<Similarity2ClassIndex> simiList = new ArrayList<Similarity2ClassIndex>();
		for (int i = 0; i < codeSnippets.size(); i++) {
			SourceCode is = new SourceCode(codeSnippets.get(i));
			Similarity2ClassIndex s2c = new Similarity2ClassIndex();
			s2c.setClassId(i);
			s2c.setSimilarity(getSimilarity(seed, is));
			simiList.add(s2c);

			FirstLastRow.clear();
		}
		Collections.sort(simiList);
		int i = 0;
		for (Similarity2ClassIndex s2c : simiList) {
			System.out.println(s2c.getClassId() + "  " + s2c.getSimilarity() + "   " + insFiles[s2c.getClassId()]);
			codes.add(insFiles[s2c.getClassId()].getName());
			i++;
			if (i > 11) {
				break;
			}
		}
		return codes;
	}

	/**
	 * �ڼ���Ĺ����м���LC;
	 */
	public static List<Similarity2ClassIndex> BWTSearch2(SourceCode seed) {
		List<Similarity2ClassIndex> simiList = new ArrayList<Similarity2ClassIndex>();
		for (int i = 0; i < codeSnippets.size(); i++) {
			SourceCode is = new SourceCode(codeSnippets.get(i));
			Similarity2ClassIndex s2c = new Similarity2ClassIndex();
			s2c.setClassId(i);
			s2c.setSimilarity(getSimilarity(seed, is));
			simiList.add(s2c);

			FirstLastRow.clear();
		}
		Collections.sort(simiList);
		return simiList;
	}

	/**
	 * ͨ����һ�ַ�������������InstructionSequence ֮������ƶ�; ���ڼ���Ĺ�����������LC Sequence;
	 */
	public static double getSimilarity(SourceCode seed, SourceCode freq) {
		// System.out.println(seed.getFileName());
		// �������;
		getFirstLastRow(new SourceCode(freq));
		List<Integer> firstRowMap = mapRows(FirstLastRow.get(0));
		List<Integer> lastRowMap = mapRows(FirstLastRow.get(1));

		double s1 = (BWTSimilarity(firstRowMap, lastRowMap, seed.getCodes()) + 1) / seed.getCodes().size();
		firstRowMap.clear();
		lastRowMap.clear();
		FirstLastRow.clear();

		return (s1);
	}

	/**
	 * BWT �ִ�ƥ�����ƶȼ����㷨ʵ��;
	 */
	public static double BWTSimilarity(List<Integer> firstRowMap, List<Integer> lastRowMap, List<TokenList> seedList) {
		double similarScore = 0.0;
		double threshold = 0.0;
		List<Integer> startPointList = null;
		for (int i = 0; i < 3; i++) {
			if (seedList.size() - 1 - i < 0) {
				break;
			}
			startPointList = getStartPoint(seedList.get(seedList.size() - 1 - i));
			if (startPointList != null && !startPointList.isEmpty()) {
				break;
			}
		}
		if (startPointList == null || startPointList.isEmpty()) {
			return 0.0;
		}
		if (startPointList.contains(-1)) {
			return 0.0;
		}

		// System.out.println("startPointList: " + startPointList);
		for (int i = 0; i < startPointList.size(); i++) {
			int startPoint = startPointList.get(i);
			// System.out.println("startPointList: " + startPointList.get(i));
			double tempSimilarity = 0.0;
			TokenList freqOp = null;
			TokenList freqOp1 = null;
			threshold = 0;
			for (int j = seedList.size() - 2; (j > -1) && (threshold < 3); j--) {
				// System.out.println(i + " " + j);
				double dtemp = -1.0;
				TokenList seedOp = seedList.get(j);
				List<TokenList> freqTokenList = BurrowsWheelerTransform(firstRowMap, lastRowMap, startPoint, 2);
				if (freqTokenList == null) {
					break;
				} else if (freqTokenList.size() > 1) {
					freqOp = freqTokenList.get(0);
					freqOp1 = freqTokenList.get(1);
					// System.out.println("freqOpList: " + freqOpList.size());
				} else if (freqTokenList.size() > 0) {
					freqOp = freqTokenList.get(0);
					// System.out.println("freqOpList: " + freqOpList.size());
				} else {
					// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					break;
				}

				double s1 = 0.0;
				double s2 = 0.0;
				if (isEquals(freqOp, seedOp)) {
					tempSimilarity += getSimilarityBetweenLine2(seedOp, freqOp);
				} else if (freqOp1 != null) {
					s1 = getSimilarityBetweenLine2(seedOp, freqOp);
					s2 = getSimilarityBetweenLine2(seedOp, freqOp1);
					s2 -= 0.2;
					dtemp = s1 > s2 ? s1 : s2;
					tempSimilarity += dtemp;
					// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				} else {
					tempSimilarity += getSimilarityBetweenLine2(seedOp, freqOp);
				}

				if (dtemp == s2) {
					threshold += 1;
					int temp = getFirstIndex(firstRowMap, freqOp.getTokens().get(0), lastRowMap.get(startPoint));
					startPoint = getFirstIndex(firstRowMap, freqOp1.getTokens().get(0), lastRowMap.get(temp));
					if (startPoint == -1) {
						break;
					}
				} else {
					startPoint = getFirstIndex(firstRowMap, freqOp.getTokens().get(0), lastRowMap.get(startPoint));
					if (startPoint == -1) {
						break;
					}
				}
			}
			// System.out.println(tempSimilarity);
			if (tempSimilarity > similarScore) {
				similarScore = tempSimilarity;
			}
		}
		return similarScore;
	}

	/**
	 * �ж�����TokenList �Ƿ����;
	 */
	public static boolean isEquals(TokenList list1, TokenList list2) {
		if (getSimilarityBetweenLine2(list1, list2) > 0.8) {
			return true;
		}
		return false;
	}

	/**
	 * ��������TokenList �����ƶ�;
	 */
	public static double getSimilarityBetweenLine(TokenList list1, TokenList list2) {
		Set<String> set1 = new HashSet<String>(list1.getTokens());
		Set<String> set2 = new HashSet<String>(list2.getTokens());

		Set<String> intersection = new HashSet<String>();
		intersection.addAll(set1);
		intersection.retainAll(set2);

		Set<String> union = new HashSet<String>();
		union.addAll(set1);
		union.addAll(set2);
		/*
		 * System.out.println("!!!!!!!!!!!!!!!!!" + 1.0 * intersection.size() /
		 * union.size() + " " + intersection.size() + " " + union.size());
		 * if(1.0 * intersection.size() / union.size() == 0){ for(String s :
		 * set1){ System.out.print(s + " "); } System.out.print(
		 * " +++++++++++++++++++++++++++++++++++++++++++ "); for(String s :
		 * list1.getTokens()){ System.out.print(s + " "); }
		 * System.out.println(""); for(String s : set2){ System.out.print(s +
		 * " "); } System.out.print(
		 * " +++++++++++++++++++++++++++++++++++++++++++ "); for(String s :
		 * list2.getTokens()){ System.out.print(s + " "); }
		 * System.out.println(""); }
		 */
		return (1.0 * intersection.size() / union.size());
	}

	/**
	 * ��������TokenList �����ƶ�;
	 */
	public static double getSimilarityBetweenLine2(TokenList list1, TokenList list2) {

		if (list2.getTokens().containsAll(list1.getTokens())) {
			return 1.0;
		}

		int length1 = list1.getTokens().size();
		int length2 = list2.getTokens().size();
		Set<String> set1 = new HashSet<String>(list1.getTokens());
		int k1 = 2;
		double b = 0.75;
		double score = 0.0;
		int avgdl = 10;
		for (String s : set1) {
			int count1 = Collections.frequency(list1.getTokens(), s);
			// double weight = Math.log(1.0*length1/count1);
			double weight = 1.0 * count1 / length1;
			int f1 = Collections.frequency(list2.getTokens(), s);
			double r = f1 * (k1 + 1) / (f1 * (k1 + 0.85) + (1 - b + 1.0 * b * length2 / avgdl));
			score += weight * r;
			// System.out.println("!!!!!!!!!!" + count1 + " " + weight + " " +
			// f1 + " " + r + " " + score);
		}
		return score;
	}

	/**
	 * ��ȡseed��freq�еĿ�ʼλ���б�;
	 */
	public static List<Integer> getStartPoint(TokenList seedLast) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < FirstLastRow.get(0).size(); i++) {
			if (FirstLastRow.get(0).get(i).getTokens().get(0).equals(seedLast.getTokens().get(0))) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * BWT �㷨ʵ��; startPosition ��ʾLastList�п�ʼ��λ��; �����Ҫ����ԭ�� startPosition = 0;N =
	 * FirstLastRow.get(1).size()-1;
	 */
	public static List<TokenList> BurrowsWheelerTransform(List<Integer> firstRowMap, List<Integer> lastRowMap,
			int startPosition, int N) {
		int Lindex = startPosition;
		int Findex = 0;
		String preToken = null;
		int preNum = 0;
		List<TokenList> list = new ArrayList<TokenList>();

		for (int i = 0; (i < N) && (FirstLastRow.get(1).size() > Lindex)
				&& !(FirstLastRow.get(1).get(Lindex).getTokens().get(0).equals(" ")); i++) {
			list.add(FirstLastRow.get(1).get(Lindex));
			// System.out.println("List add: " +
			// FirstLastRow.get(1).get(Lindex).getCodeId());
			preToken = FirstLastRow.get(1).get(Lindex).getTokens().get(0);
			preNum = lastRowMap.get(Lindex);
			Findex = getFirstIndex(firstRowMap, preToken, preNum);
			// System.out.println("Findex: " + Findex);
			if (Findex == -1) {
				System.out.println("��������");
				break;
			}
			Lindex = Findex;
		}
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// for(TokenList tl : list){
		// for(String s : tl.getTokens())
		// System.out.print(s + " ");
		// System.out.println();
		// }

		return list;
	}

	/**
	 * ����FirstList��LastList �е�ÿ��Ԫ���ǵڼ��γ���;
	 */
	public static List<Integer> mapRows(List<TokenList> row) {
		List<Integer> list = new ArrayList<Integer>();
		Map<String, Integer> RowMap = new HashMap<String, Integer>();
		// int i = 0;
		for (TokenList tl : row) {
			String token = tl.getTokens().get(0);

			if (RowMap.keySet().contains(token)) {
				int value = RowMap.get(token) + 1;
				list.add(value);
				RowMap.replace(token, value);
				// System.out.println("list add: " + value);
			} else {
				RowMap.put(token, 1);
				list.add(1);
				// System.out.println("list add: " + 1);
			}
		}

		return list;
	}

	/**
	 * �õ�FirstRow �� LastRow;
	 */
	public static void getFirstLastRow(SourceCode code) {
		List<SourceCode> scList = new ArrayList<SourceCode>();

		// ���$����;
		TokenList tl = new TokenList();
		List<String> templist = new ArrayList<String>();
		templist.add(" ");
		tl.setTokens(templist);
		List<TokenList> tll = code.getCodes();
		tll.add(0, tl);
		code.setCodes(tll);

		SourceCode locaCode = new SourceCode(code);
		for (int i = 0; i < code.getCodes().size(); i++) {
			SourceCode temp = new SourceCode(locaCode);
			List<TokenList> tempTokenList = temp.getCodes();
			TokenList tempToken = new TokenList(tempTokenList.get(tempTokenList.size() - 1));
			tempTokenList.remove(tempTokenList.size() - 1);
			tempTokenList.add(0, tempToken);
			temp.setCodes(tempTokenList);

			scList.add(temp);

			locaCode = temp;
		}

		// for(SourceCode sc : scList){
		// for(TokenList temptl : sc.getCodes()){
		// for(String s : temptl.getTokens()){
		// System.out.print(s + " ");
		// }
		// }
		// System.out.println();
		// }

		// ����;
		Collections.sort(scList);
		// System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		// for(SourceCode sc : scList){
		// for(TokenList temptl : sc.getCodes()){
		// for(String s : temptl.getTokens()){
		// System.out.print(s + " ");
		// }
		// }
		// System.out.println();
		// }

		List<TokenList> firstRow = new ArrayList<TokenList>();
		List<TokenList> lastRow = new ArrayList<TokenList>();
		for (SourceCode ins : scList) {
			firstRow.add(ins.getCodes().get(0));
			lastRow.add(ins.getCodes().get(ins.getCodes().size() - 1));
		}
		FirstLastRow.add(firstRow);
		FirstLastRow.add(lastRow);
	}

	/**
	 * ����LastList�еĵڼ��γ��ֵ�ĳ��Ԫ�ص�λ��,���ض�Ӧ��FirstList�е�����;
	 */
	public static int getFirstIndex(List<Integer> firstRowMap, String preLine, int preNum) {
		for (int i = 0; i < firstRowMap.size(); i++) {
			if ((firstRowMap.get(i) == preNum) && (FirstLastRow.get(0).get(i).getTokens().get(0).equals(preLine))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * ��ȡ����;��ʽ������;
	 */
	private static void readCode(String filePath) {
		File directory = new File(filePath);
		insFiles = directory.listFiles();
		for (int i = 0; i < insFiles.length; i++) {
			List<TokenList> linecode = readCodeFromFile(filePath + insFiles[i].getName());
			SourceCode sc = new SourceCode();
			sc.setCodes(linecode);
			sc.setId(i);
			sc.setName(insFiles[i].getName());
			codeSnippets.add(sc);

			if (i > 200)
				break;
		}
	}

	/**
	 * ���ļ��ж�ȡ����;
	 */
	private static List<TokenList> readCodeFromFile(String path) {
		List<TokenList> code = new ArrayList<TokenList>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; // ���ڰ�װInputStreamReader,��ߴ������ܡ���ΪBufferedReader�л���ģ���InputStreamReaderû�С�
		try {
			String str = "";
			fis = new FileInputStream(path);// FileInputStream
			// ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
			isr = new InputStreamReader(fis);// InputStreamReader ���ֽ���ͨ���ַ���������,
			br = new BufferedReader(isr);// ���ַ��������ж�ȡ�ļ��е�����,��װ��һ��new
											// InputStreamReader�Ķ���
			while ((str = br.readLine()) != null) {
				TokenList tl = new TokenList();
				tl.setTokens(getTokenList(str));
				if (!tl.getTokens().isEmpty())
					code.add(tl);
			}
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ���ָ���ļ�");
		} catch (IOException e) {
			System.out.println("��ȡ�ļ�ʧ��");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// �رյ�ʱ����ð����Ⱥ�˳��ر���󿪵��ȹر������ȹ�s,�ٹ�n,����m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	/**
	 * ��Դ��תΪ�ʵļ���; �����շ������»��߷ִ�;
	 */
	private static List<String> getTokenList(String code) {
		List<String> list = new ArrayList<String>();
		String[] tokens = code.replaceAll("\\{|\\}|\\(|\\)|\\,|;|=|\\\"|\\.|!|&|\\|", " ").split(" ");
		for (String t : tokens) {
			if (t != null && !t.equals("") && !t.equals(" ")) {
				list.addAll(fotmatToken(t));
			}
		}
		return list;
	}

	/**
	 * ������ͬ��ת��ΪСд; ��ȥ��ĩβ������;
	 */
	private static List<String> fotmatToken(String code) {

		// ȥ��ĩβ������;
		char[] codes = code.toCharArray();
		for (int i = codes.length - 1; i >= 0; i--) {
			if (codes[i] <= '9' && codes[i] >= '0') {
				codes[i] = '_';
			} else {
				break;
			}
		}
		String token = new String(codes);

		// �ִʺʹ�Сдת��;
		List<String> list = new ArrayList<String>();
		if (token.contains("_")) {
			String[] words = token.split("_");
			Collections.addAll(list, words);
		} else {
			list.add(token);
		}

		// �ִ�;
		list = splitWord(list);

		return list;
	}

	/**
	 * �����շ������»��߷ִ�;
	 */
	private static List<String> splitWord(List<String> list) {
		List<String> addList = new ArrayList<String>();
		List<String> removeList = new ArrayList<String>();
		for (String word : list) {
			int location = 0;
			int flag = 0;
			for (int i = 0; i < word.length(); i++) {
				char c = word.charAt(i);
				if (Character.isUpperCase(c)) {
					// if((i+1 < word.length() &&
					// !Character.isUpperCase(word.charAt(i+1))) && (i-1 >= 0 &&
					// !Character.isUpperCase(word.charAt(i-1)))){
					if (i - 1 >= 0 && !Character.isUpperCase(word.charAt(i - 1))) {
						String word1 = word.substring(location, i);
						addList.add(word1.toLowerCase());
						location = i;
						removeList.add(word);
						flag = 1;
					}
				}
			}
			if (flag == 1) {
				String word2 = word.substring(location, word.length());
				addList.add(word2.toLowerCase());
			} else {
				addList.add(word.toLowerCase());
				removeList.add(word);
			}
		}
		list.removeAll(removeList);
		list.addAll(addList);
		return list;
	}
	
	/**
	 * ���ļ��ж�ȡ����;
	 */
	public static String getCodeFromFile(String path) {
		String code = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; // ���ڰ�װInputStreamReader,��ߴ������ܡ���ΪBufferedReader�л���ģ���InputStreamReaderû�С�
		try {
			String str = "";
			fis = new FileInputStream(path);// FileInputStream
			// ���ļ�ϵͳ�е�ĳ���ļ��л�ȡ�ֽ�
			isr = new InputStreamReader(fis);// InputStreamReader ���ֽ���ͨ���ַ���������,
			br = new BufferedReader(isr);// ���ַ��������ж�ȡ�ļ��е�����,��װ��һ��new
											// InputStreamReader�Ķ���
			while ((str = br.readLine()) != null) {
					code += str;
					code += "\n";
			}
		} catch (FileNotFoundException e) {
			System.out.println("�Ҳ���ָ���ļ�");
		} catch (IOException e) {
			System.out.println("��ȡ�ļ�ʧ��");
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
				// �رյ�ʱ����ð����Ⱥ�˳��ر���󿪵��ȹر������ȹ�s,�ٹ�n,����m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return code;
	}
}