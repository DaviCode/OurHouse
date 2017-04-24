/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abitazione;

/**
 *
 * @author David
 */
public class Abitazione {
    private static String cod_abitazione;
    private static String nome_abitazione;
    
    
    public void set(String cod_abitaz,String nome_abitaz)
    {
        cod_abitazione=cod_abitaz;
        nome_abitazione=nome_abitaz;
    }
    
    public String get()
    {
        return nome_abitazione;
    }
    
}

