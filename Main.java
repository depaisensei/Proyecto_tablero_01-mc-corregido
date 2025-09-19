public class Main {
    public static Tablero juego; //Declarar el objeto de tipo tablero dentro del main, se vuelve un atributo global de la clase main
   public static Dimension dim;
   
    /*validacion del tamaño de las filas y columnas, además del numero de generaciones */ 
    public static boolean IngresarTamaño(String entrada){
        try{
            String[] entradaSeparada=entrada.split(",");
             //separar el arreglo y asignarlo a cada variable
             int f=Integer.parseInt(entradaSeparada[0]);
            int  c=Integer.parseInt(entradaSeparada[1]);
            int  g=Integer.parseInt(entradaSeparada[2]);
         if (!((f<=20 && f>=2) && (c<=20 && c>=2))){ //validar que filas y columnas esten en el rango de 2 a 20
             System.err.println("Error en el tamaño de las filas y columnas ingresadas, fuera de rango");
             return false; 
            }
             if (!(g<=50 && g>=1)){ //validar que las generaciones sean minimo 1 y máximo 50
                 System.err.println("Error en la cantidad de generaciones, fuera de rango"); 
                 return false;
                } 
                dim= new Dimension(f, c, g);
                return true;
            }
            catch (NumberFormatException e){
            System.err.println("Error en el tipo de dato ingresado ");
            return false;
        }
        }



     /*validar que el primer dato sea impar, además de que la cantidad de seres vivos no sean más del 50% del tamaño de la tabla */   
    public static boolean ValidarEntradaInicio(String entrada){
         String[] entradaSeparada=entrada.split(",");  //se separa la entrada y se guarda en un arreglo tipo String
        int contador=juego.regresarContador(entradaSeparada);
        int n;
         n=(Integer.parseInt(entradaSeparada[0]));
         try{
        if (n % 2==0){
            System.err.println("El valor no es impar");
            return false;
        } 
        //validar que los seres deseados a crear sean menor al 50% de la tabla
            int t=juego.calcularTamañoTabla();
            if(n>(t/2)){
                System.err.println("La cantidad de seres vivos a crear, no puede ser mayor al 50% del tamaño de la tabla");
                return false;      
        }  
        //Validar que los datos ingresados correspondan a la cantidad de seres vivos establecidos
               if(contador!=n*2){
            System.out.println("la cantidad de coordenadas es distinta a la introducida");
            System.out.println("(Conjunto de datos esperados: "+n+").");
            return false;
               } 
               //verificar para el arreglo de los puntos X, que estos sean iguales al número de filas
               //se recorre cada elemento dentro del arreglo posx y se compara con el tamaño de las filas, para asegurarse que se encuentra dentro.
               for (Object DatoX : juego.getX()) {
                   int filas = juego.getFilas();
                   int columnas=juego.getColumnas();
                   if ((int)DatoX > columnas) {
                       System.err.println("X no puede ser mayor al numero de columnas");
                       System.err.println("(Limites actuales:"+filas+","+columnas+")");
                       return false;
                       
                   }
               }
               //verificar para  el arreglo de los puntosY,que estos sean iguales al número de columnas
               for (Object DatoY : juego.getY()) {
                int columnas= juego.getColumnas();
                 int filas = juego.getFilas();
                if ((int)DatoY>filas){
                    System.err.println("Y no puede ser mayor al numero de filas");
                    System.err.println("(Limites actuales:"+filas+","+columnas+")");
                       return false;
                }     
               }
        return true;
    }             
    catch (NumberFormatException e){
        System.err.println("Error al tratar de convertir los datos a int");
        return false;
    }  
}


    public static void main(String[] args) {
        //generar nueva instancia
        boolean validar2;
        String entrada1;
        do{  
             System.err.println("Dame el tamaño de filas, columnas y generación, separado por comas");
             String entrada=Keyboard.readString().trim();
                           //Validar que los datos de filas, columnas y generaciones esten en el rango
            validar2=IngresarTamaño(entrada);
            }
            while(validar2!=true);
    //se genera la instancia con los datos validados ingresados anteriormente
     juego= new Tablero(dim.getFilas(),dim.getColumnas(),dim.getGeneraciones());
         boolean validar;
         //inicio del juego preguntando datos iniciales
        do { 
        System.out.println("Dame el numero de seres vivos a crear (impar), además de sus cordenadas x y separandolas por ','");
        entrada1=Keyboard.readString().trim();
        validar=ValidarEntradaInicio(entrada1);
        } while(!validar);
        //si se pasa la validación, comenzar el juego
        juego.iniciarlizar(entrada1); //generar primera generación, luego ingresar el string
        //inicializar separar los trabajos
      // System.out.print(juego);
       

    }
    
}


