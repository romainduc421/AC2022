import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProgramComparator{

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
	public static boolean compare(Vector<Instruction> p1, Vector<Instruction> p2){
			int primeNumber = RandomPrime.randomPrime();
			return evaluateur(p1,primeNumber) == evaluateur(p2,primeNumber);
	}

	private static int calculateMod(String op, Integer x, Integer x2, int p){
		int res=0;
		if (op.equals("+"))  
			res = (x.intValue() + x2.intValue())%p;
		else if (op.equals("-"))  
			res = (x.intValue() - x2.intValue())%p;
		else if (op.equals("*"))  
			res = multiply(x, x2, p);
		return res;
	}

	private static int multiply(int x, int y, int p) {
		int result = 0;
		while (y > 0) {
			//if y est impair, ajouter 'x' au résultat
			if ((y & 1) == 1) result = (result + x) % p;
			y >>= 1;
			x = (2 * x) % p;
		}
		return result;
	}

	/**
	 * evalue le programme modulo p
	 * @param program : vecteur d'instructions
	 * @param p : entier du modulo
	 * @return
	 */
	public static int evaluateur(Vector<Instruction> program, int p) {
		int res=0;
		Map<String, Integer> values  = new HashMap<>();
		for(Instruction i: program){
			if (i instanceof Assign){
				Assign a = (Assign) i;
				values.put(a.lhs, (int)((Entier)a.rhs).x);
			}
			else{		
				AssignOperator a = (AssignOperator) i;
				//System.out.println("On effectue l'opération " + a.op + " aux valeurs " + a.t0  + " et " + a.t1 + " et on stocke le résultat dans " + a.lhs);
				String oper = a.op;
				if(a.t0 instanceof Entier){
					if(a.t1 instanceof Entier)
						values.put(a.lhs, calculateMod(oper,((Entier)a.t0).x,((Entier)a.t1).x,p));
					else{
						if(values.get(((Variable)a.t1).var) == null) 
							values.put(a.lhs, calculateMod(oper,((Entier)a.t0).x,Integer.valueOf(0),p));
						else 
							values.put(a.lhs, calculateMod(oper,((Entier)a.t0).x,values.get(((Variable)a.t1).var),p));
					}
				}
				else {
					if(a.t1 instanceof Entier){
						if(values.get(((Variable)a.t0).var) == null)
							values.put(a.lhs, calculateMod(oper,Integer.valueOf(0), ((Entier)a.t1).x,p));
						else 
							values.put(a.lhs, calculateMod(oper,values.get(((Variable)a.t0).var),((Entier)a.t1).x,p));
					}
					else{	//2 variables
						if(values.get(((Variable)a.t0).var) == null){
							if(values.get(((Variable)a.t1).var) == null) 
								values.put(a.lhs,calculateMod(oper, Integer.valueOf(0), Integer.valueOf(0),p));
							else 
								values.put(a.lhs, calculateMod(oper,Integer.valueOf(0),values.get(((Variable)a.t1).var),p));
						}else{
							if(values.get(((Variable)a.t1).var) == null) 
								values.put(a.lhs, calculateMod(oper, values.get(((Variable)a.t0).var), Integer.valueOf(0),p));
							else 
								values.put(a.lhs, calculateMod(oper,values.get(((Variable)a.t0).var), values.get(((Variable)a.t1).var),p));
						}
					}
				}
				res = values.get(a.lhs);
			}
		}
		return res;
	}

	public static void main(String[] args){
		if(args.length != 2){
			System.err.print("Erreur usage :\n\tProgramComparator <prgm1> <prgm2>");
			System.exit(-1);
		}

		Vector<Instruction> x1 = Parser.parse(FileToString(args[0]));
		Vector<Instruction> x2 = Parser.parse(FileToString(args[1]));

		System.out.println(ProgramComparator.compare(x1, x2));

	}
}