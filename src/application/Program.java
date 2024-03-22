package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		List<Sale> list = new ArrayList<>();
		
		System.out.print("Enter file full path: ");
		String path = sc.nextLine();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
                Integer month = Integer.parseInt(fields[0]);
                Integer year = Integer.parseInt(fields[1]);
                String seller = (fields[2]);
                Integer items = Integer.parseInt(fields[3]);
                Double total = Double.parseDouble(fields[4]);
                
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			List<Sale> sales2016 = list.stream()
										.filter(sale -> sale.getYear() == 2016)
										.collect(Collectors.toList());
			
			
			sales2016.sort(Comparator.comparingDouble(Sale::averagePrice().reversed()));
			
			sales2016.stream().limit(5).collect(Collectors.toList());
			
			Double loganReport = list.stream()
										 .filter(sale -> sale.getMonth() < 8 || sale.getMonth() > 0 )
										 .filter(sale -> sale.getSeller().equals("Logan"))
	                                     .mapToDouble(Sale::getAmount)
	                                     .sum();
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			sales2016.forEach(System.out.println());

			System.out.println("Valor total vendido pelo Logan nos meses 1 e 7 = " + loganReport);
			
			
		 } catch (IOException e) {
	            System.out.println("O sistema não pode encontrar o arquivo especificado " + e.getMessage());
	            
	            sc.close();
	        }
	}

}
