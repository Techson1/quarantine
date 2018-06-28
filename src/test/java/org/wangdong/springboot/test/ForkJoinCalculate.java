package org.wangdong.springboot.test;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.beiqisoft.aoqun.util.MyUtils;

public class ForkJoinCalculate {
	public static void main(String[] args) {
//		Breed breed = new Breed();
//		breed.setBreedIds("1,2");
//		System.out.println(breed.getBloodIncludeBlood("1,2"));
		String k ="";
		/*Arrays.asList(k.split(",")).stream().sorted((a,b)->MyUtils.strToLong(a)
				.compareTo(MyUtils.strToLong(b))).collect(Collectors.joining(",")));*/
		String t=Arrays.asList(k.split(",")).stream().sorted((a,b) -> MyUtils.strToLong(a)
				.compareTo(MyUtils.strToLong(b))).collect(Collectors.joining(","));
		System.out.println(t);
	}
}   
