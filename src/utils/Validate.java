/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author ndhlt
 */
public class Validate {

    /**
     *Validate the given String whether its resembles a Unicode name
     * 
     * @param name The string to be validated
     * @return true if its a name, otherwise false
     */
    public static boolean name(String name) {
        String regex = "\\p{L}+[\\p{L}\\p{M}\\p{Pd}'’]*\\p{L}+";
        return name.matches(regex);
    }
    
    public static boolean checkName(String name){
        String regax = "[a-zA-Z ÀÁÂÃÈÉÊẾÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêếìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỂưăạảấầẩẫậắằẳẵặẹẻẽềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]+";
        return name.matches(regax);      
    }

    /**
     *Validate the given String whether its resembles an email address
     * 
     * @param email The string to be validated
     * @return true if its an email, otherwise false
     */
    public static boolean email(String email) {
        String regex = "\\w{1,20}+@\\w{1,5}+(\\.\\w{1,5}){1,2}";
        return email.matches(regex);
    }

    /**
     *Validate the given String whether its resembles a phone number
     * 
     * @param numbers The string to be validated
     * @return true if its a phone number, otherwise false
     */
    public static boolean phoneNumber(String numbers) {
        String regex = "0\\d{9,10}";
        return numbers.matches(regex);
    }

    /**
     *Validate the given String whether its resembles an Id number
     * 
     * @param numbers The string to be validated
     * @return true if its an Id number, otherwise false
     */
    public static boolean idNumber(String numbers) {
        String regex = "0\\d{8,11}";
        return numbers.matches(regex);
    }

    /**
     *Validate the given String whether its resembles a decimal number
     * 
     * @param number The string to be validated
     * @return true if its a decimal number, otherwise false
     */
    public static boolean number(String number) {
        try {
            Double.valueOf(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     *Validate the given String whether its resembles a positive decimal number
     * 
     * @param number The string to be validated
     * @return true if its a positive decimal number, otherwise false
     */
    public static boolean positiveNumb(String number) {
        try {
            double i = Double.parseDouble(number);
            return i >= 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     *Validate the given String whether its resembles an integer
     * 
     * @param number The string to be validated
     * @return true if its an integer, otherwise false
     */
    public static boolean integer(String number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     *Validate the given String whether its resembles a positive integer
     * 
     * @param number The string to be validated
     * @return true if its a positive integer, otherwise false
     */
    public static boolean positiveInt(String number) {
        try {
            int i = Integer.parseInt(number);
            return i >= 0;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
