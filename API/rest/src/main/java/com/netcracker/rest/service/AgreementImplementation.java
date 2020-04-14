package com.netcracker.rest.service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.netcracker.rest.model.Agreement;
import com.netcracker.rest.model.Product;

@Service
public class AgreementImplementation implements AgreementService{
	
	@Override
	public String save(Agreement agree) {
		String directory = System.getProperty("user.home");
		String fileName = agree.getName();
	    File myObj = new File(fileName);
	    try {
	    	if (myObj.createNewFile()) {
	    		System.out.println("File created: " + myObj.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String absolutePath = directory + File.separator + fileName;

		// write the content in file 
		try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {
		    String fileContent = agree.getName() + "," + agree.getSignedBy() + ",\n";
		    List <Product> products = agree.getProducts();
		    
	        for (Product prod : products) {
	        	fileContent += prod.getName() + "," + prod.getPrice() + "," + prod.getParent() + ",\n";
	        }
		    
		    bufferedWriter.write(fileContent);
		} catch (IOException e) {
		    // exception handling
		}
		return absolutePath;
	}

	@Override
	public Agreement read(String file) {
		String directory = System.getProperty("user.home");
		String absolutePath = directory + File.separator + file;
		
		Agreement agree = null;
		List<Product> prod = new ArrayList<Product>();
		int i = 0;
		// read the content from file
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
		    String line = bufferedReader.readLine();
		    while(line != null) {
		    	String elements[] = line.split(","); 
		    	if(i == 0) {
		    		agree = new Agreement(elements[0],elements[1]);
		    	} else {
		    		//List<Product> aux = new ArrayList<Product>();
		    		prod.add(new Product(elements[0],Float.parseFloat(elements[1]),elements[2]));
		    	}
		        System.out.println(line);
		        line = bufferedReader.readLine();
		        i++;
		    }
		    agree.setProducts(getChildren(prod));
		} catch (FileNotFoundException e) {
		    // exception handling
		} catch (IOException e) {
		    // exception handling
		}
		return agree;
	}

	private List<Product> getChildren(List<Product> products) {
		int n = products.size();
		List<Product> initial = new ArrayList<Product>();
		List<Integer> indexes = new ArrayList<>();
		
        for (int i = n - 1; i >= 0; i--) {
        	if(!indexes.contains(i)) {
        			List<Product> aux = new ArrayList<Product>();
                    Product current = products.get(i);
                    
                    int parentNum = -1;
                    for(int j = i - 1; j >= 0; j--) {
                    	Product next = products.get(j);
                    	if(current.getParent() == next.getParent()) {
                    		aux.add(next);
                    		indexes.add(j);
                    	}
                    	if(current.getParent() == next.getName()) {
                    		parentNum = j;
                    	}
                    }
                    if(parentNum != -1)
                    	products.get(parentNum).setChildren(aux);
                    
                    if(current.getParent().matches("Agreement(.*)")) {
                    	initial.add(current);
                    }
        	} else {
        		if(indexes.size()<1)
        			indexes.remove(indexes.indexOf(i));
        	}
        }
        return initial;
	}
}
