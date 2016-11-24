package com.ibm.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class ListSortUtil {

	public static List<List<String>> sortListByIndexOf(List<List<String>> list, final int index) {

		if (list != null) {
			Comparator<List<String>> comparator = new Comparator<List<String>>() {

				@Override
				public int compare(List<String> param1, List<String> param2) {

					String string1 = null;
					String string2 = null;
					string1 = param1.get(index);
					string2 = param2.get(index);
					if (StringUtils.isBlank(string1) && StringUtils.isNotBlank(string2)) {
						return 1;
					} else if (StringUtils.isNotBlank(string1) && StringUtils.isBlank(string2)) {
						return -1;
					} else if (StringUtils.isBlank(string1) && StringUtils.isBlank(string2)) {
						return 0;
					}
					if (!isNumeric(string1) && isNumeric(string2)) {
						return 1;
					} else if (isNumeric(string1) && !isNumeric(string2)) {
						return -1;
					} else if (!isNumeric(string1) && !isNumeric(string2)) {
						return 0;
					}

					BigDecimal data1 = new BigDecimal(string1);
					BigDecimal data2 = new BigDecimal(string2);

					return data2.compareTo(data1);
				}
			};

			try {
				Collections.sort(list, comparator);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	public static List<List<String>> sortListByIndexAsc(List<List<String>> list, final int index) {

		if (list != null) {
			Comparator<List<String>> comparator = new Comparator<List<String>>() {

				@Override
				public int compare(List<String> param1, List<String> param2) {

					String string1 = null;
					String string2 = null;
					string1 = param1.get(index);
					string2 = param2.get(index);
					if (StringUtils.isBlank(string1) && StringUtils.isNotBlank(string2)) {
						return 1;
					} else if (StringUtils.isNotBlank(string1) && StringUtils.isBlank(string2)) {
						return -1;
					} else if (StringUtils.isBlank(string1) && StringUtils.isBlank(string2)) {
						return 0;
					}
					if (!isNumeric(string1) && isNumeric(string2)) {
						return 1;
					} else if (isNumeric(string1) && !isNumeric(string2)) {
						return -1;
					} else if (!isNumeric(string1) && !isNumeric(string2)) {
						return 0;
					}

					BigDecimal data1 = new BigDecimal(string1);
					BigDecimal data2 = new BigDecimal(string2);

					return data1.compareTo(data2);
				}
			};

			try {
				Collections.sort(list, comparator);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	public static List<List<String>> sortListAddNo(List<List<String>> lst, final int n) {
		String tempstr = null;
		int index = 1;
		for (int i = 0; i < lst.size(); i++) {
			if (tempstr == null) {
				lst.get(i).add(Integer.toString(index));
				tempstr = lst.get(i).get(n);
			} else if (tempstr.equals(lst.get(i).get(n))) {
				lst.get(i).add(Integer.toString(index));
			} else {
				lst.get(i).add(Integer.toString(i + 1));
				index = i + 1;
				tempstr = lst.get(i).get(n);
			}
		}
		return lst;
	}

	public static boolean isNumeric(String str) {
		int dotNo = 0;
		int lineNo = 0;
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				if ('.' == str.charAt(i)) {
					dotNo++;
					continue;
				} else if ('-' == str.charAt(i)) {
					lineNo++;
					continue;
				} else {
					return false;
				}
			}
			if (dotNo > 1 || lineNo > 1) {
				return false;
			}
		}
		return true;
	}
}
