package br.com.rekome.interfaces;

public interface ValidationInterface {
	
	public default void execute(){
		throw new RuntimeException();
	}
}
