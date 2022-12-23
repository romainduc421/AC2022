import java.util.*;

import java.io.FileInputStream;
import java.io.File;




public class Toto{

	private static Integer calculate(String op, Integer x, Integer x2){
		Integer res=0;
		if (op.equals("+"))  
        	res = x + x2;
        else if (op.equals("-"))  
       		res = x - x2;
        else if (op.equals("*"))  
        	res = x * x2;
		return res;
	}

    public static String FileToString(String filename){
		try{
			
			File file = new File(filename);
			FileInputStream f = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			f.read(data);
			f.close();
			
			return new String(data, "UTF-8");
		}
		catch(Exception ex)
	    {
			ex.printStackTrace();
			return null;
	    }       
    }

    public static void main(String[] args){
		//String s = "x=1;y=x*2;z=x-y;x=y+z";	//1
		Vector<Instruction> x = Parser.parse(FileToString("prog1"));
		//Vector<Instruction> x = Parser.parse(s);
		Map<String,Integer> values = new HashMap<>();
		int res = 0;
		for(Instruction i: x){
			if (i instanceof Assign){
				Assign a = (Assign) i;
				//System.out.println("La variable " + a.lhs + " reçoit la valeur " + a.rhs);
				values.put(a.lhs, (int)((Entier)a.rhs).x);
				//System.out.println(values)
			}
			else{		
				AssignOperator a = (AssignOperator) i;
				//System.out.println("On effectue l'opération " + a.op + " aux valeurs " + a.t0  + " et " + a.t1 + " et on stocke le résultat dans " + a.lhs);
				String oper = a.op;
				if(a.t0 instanceof Entier){
					if(a.t1 instanceof Entier)
						values.put(a.lhs, calculate(oper,((Entier)a.t0).x,((Entier)a.t1).x));
					else{
						if(values.get(((Variable)a.t1).var) == null) 
							values.put(a.lhs, calculate(oper,((Entier)a.t0).x,Integer.valueOf(0)));
						else 
							values.put(a.lhs, calculate(oper,((Entier)a.t0).x,values.get(((Variable)a.t1).var)));
					}
				}
				else {
					if(a.t1 instanceof Entier){
						if(values.get(((Variable)a.t0).var) == null)
							values.put(a.lhs, calculate(oper,Integer.valueOf(0), ((Entier)a.t1).x));
						else 
							values.put(a.lhs, calculate(oper,values.get(((Variable)a.t0).var),((Entier)a.t1).x));
					}
					else{	//2 variables
						if(values.get(((Variable)a.t0).var) == null){
							if(values.get(((Variable)a.t1).var) == null) 
								values.put(a.lhs,calculate(oper, Integer.valueOf(0), Integer.valueOf(0)));
							else 
								values.put(a.lhs, calculate(oper,Integer.valueOf(0),values.get(((Variable)a.t1).var)));
						}else{
							if(values.get(((Variable)a.t1).var) == null) 
								values.put(a.lhs, calculate(oper, values.get(((Variable)a.t0).var), Integer.valueOf(0)));
							else 
								values.put(a.lhs, calculate(oper,values.get(((Variable)a.t0).var), values.get(((Variable)a.t1).var)));
						}
					}
				}
				//System.out.println(values)
				//System.out.println(values.get(a.lhs))
				res = values.get(a.lhs);
			}
		}
		System.out.println(res);
    }
}
