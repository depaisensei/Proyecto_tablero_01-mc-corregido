/*
 * Programa el juego de la vida
 * Consiste en un tablero que se genera en base al tamaño de fila y columnas asignadas por el usuario, además de que las iteraciones serán realizadas
 * en base a las generaciones ingresadas, el numero de seres vivos se asignara a partir de un String, adémas de sus posiciones que tendran
 * despues entra un proceso de calculo para determinar que seres vivos continuan con vida, cuales mueren y cuales nacen, esto hasta finalizar
 * las generaciones o una de las condiciones de salida. 
 * Marco Antonio Aranda Sainz Fecha:18-09-25.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
/*Declaracion de variables a utilizar de forma global */
//reducir los if
//generar tablero vacio, y luego iniciarlalizar, en el constructo ingresar el num de ser
public class Tablero {

    private final int NUMERODEGENERACIONES;
    private int numeroGenActual=0;
    private final int FILAS; 
    private  final int COLUMNAS;
    private String[][] tableroActual;
    private String[][] tableroAnterior;
    private final ArrayList<Integer> posX=new ArrayList<>();
    private final ArrayList<Integer> posY=new ArrayList<>();
    private final ArrayList<String> textoTableroAcumulado=new ArrayList<>(); 
    private  String textoGenActual=" "; 
    
    //agregar esperar  el salto de linea para mostrar la siguiente gráfica
     Scanner salto = new Scanner(System.in);


    
    /*limites obtenidos a partir de tabla hecha a mano de 3x3 */
    protected int[] dx={-1,0,1,1,1,0,-1,-1};
    protected int[] dy={-1,-1,-1,0,1,1,1,0};


//getters
//Utilizados para agregar las coordenadas de x y y de forma individual
        public ArrayList<Integer> getX(){    
        return posX;
    }

     public ArrayList<Integer> getY(){
        return posY;
    }
    //regresar el tamaño de las filas, así como las columnas
    public int getFilas(){
        return FILAS;
    }
    public int getColumnas(){
        return COLUMNAS;
    }

//guardar el contenido del tablero, filas y columnas
    public String[][] getTablero(){
        return  tableroActual;
    }
//guardar los textos resumen de cada generación e ir acolmulandolo
    public ArrayList<String> getTextoAcumulado(){
        return textoTableroAcumulado;
    }
        public String getTextoGenActual(){
        return textoGenActual;
    }

    //Constructor, utilizado en la clase main
    public Tablero (int filas1, int columnas1, int numGeneraciones1){
        this.FILAS=filas1;
        this.COLUMNAS=columnas1;
        this.NUMERODEGENERACIONES=numGeneraciones1;
        generarTableroVacio();
    }

    @Override
    public String toString(){
        /*utilizando el metodo append de StringBuilder para agrupar los elementos dentro de la matriz 2D Tablero,
         * con esto se construye el tablero en formato String y se guarda en el arreglo tableroActual
         */
        StringBuilder sb= new StringBuilder("Generación actual: "+(numeroGenActual+1)+"\n");
       for (int i = 0; i < this.FILAS; i++) {
            for (int j = 0; j < this.COLUMNAS; j++) {
                sb.append(tableroActual[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
     
    }
    

//generar tablero vacio a partir del tamaño filas y columnas dado por el usuario en el main
private void generarTableroVacio(){ //privado, llamar desde el constructor
    tableroActual= new String[FILAS][COLUMNAS];
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS;j++) {
                    tableroActual[i][j]="*";
                }
            }
        }



    /*un contador para determinar cuantos espacios hay en total en el tablero, util para ser utilizado en la comparación de entrada de seres vivos,
     * con el 50% del tamaño de la tabla
     */
    public int calcularTamañoTabla(){
    int contadorTamaño;
       contadorTamaño=(FILAS*COLUMNAS);
       return contadorTamaño;
        }
    /*Se calcula la primera generación, donde se compara la posición i (fila) y j (columna) con la posicion k (posx y posy), 
     * en el caso de coincidir, la posicion [i,j] del tablero pasa de ser * a ser 0, asignando seres vivos y validar que los datos
     * ingresados sean reales en el main
     */ 
    public void generarPrimeraGen(){
        try{
       for (int i = 0; i < this.FILAS; i++) {
            for (int j = 0; j < this.COLUMNAS; j++) {
                for (int k = 0; k < posX.size(); k++) {        
        if (i==posX.get(k) && j==posY.get(k)){
            tableroActual[i][j]="o";
            String textoTemporal="\n * Nace en ("+i+", "+j+") ser vivo ";
            textoGenActual= textoGenActual.concat(textoTemporal);
                }
                }
            }
            
        }            
         } catch(Exception e){
            System.err.println("Posible error en los datos ingresados, verificar que las posiciones ingresadas sean reales");
         }
    }
    
    /*Calcular numero vecinos,recorrer todas las filas y columnas y guardar la posicion de cada iteracion en posActual x y y, de ahí 
     *recorrer los 8 cuadrantes alrededor observando si son * o  "o", de ahí hacer un contador que sume cunado los vecinos tenga
     "o" y entrar en un case para eliminar 
     o mantener el ser vivo o nacer
     */
   public void calculoNumeroVecinos(){ //seprar calcular vecinos xy, en otra función
    /*generar tablero anterior con el mismo tamaño de filas y columnas */
    tableroAnterior = new String[FILAS][COLUMNAS];
//llamar a la función que copia los datos del tablero actual en el anterior
actualizarGenTablaYTexto();
int numVecinos;
//realiza los recorridos posibles dentro de los cuadrantes y aumenta el contador de vecinos en base a ello
    for (int i = 0; i < this.FILAS; i++) {
        for (int j = 0; j < this.COLUMNAS; j++) {
            numVecinos=0;
            int n1;
            int n2;
            //aqui se generan las posibles combinaciones de movimiento, 
            for (int k = 0; k < dx.length; k++) {
                n1=i+dx[k]; //por ejemplo si i=0 , entonces se buscara la conbinacion de los posibles vecinos alrededor
                n2=j+dy[k];//              y j=0   mediante el desplazamiento relativo
                //asegurarse que los posibles casos si esten dentro de la cuadricula, de lo contrario, el contador de vecinos no entra
                if((0<=n1) && (0<=n2) && (n1<FILAS) && (n2<COLUMNAS)){
                    //Evaluar si la celda de aun lado anteriormente estaba viva
                    if ("o".equals(tableroAnterior[n1][n2])){
                            numVecinos++;
                    }
                }
             
            } 
           //actualizar posiciones de seres vivos y muertos, además de generar Strings para el registro
        actualizarSeresVYM(i,j,numVecinos);       
        }
    }
   }

   //Función que recibe i, j , NumVecinos
   public void actualizarSeresVYM(int i, int j, int numSeresVivos){
    String textoTemporal;
//CASO QUE ESTE VIVO   
        if ("o".equals(tableroAnterior[i][j])){
            switch(numSeresVivos){
            case 2,3->
            {
            //si ya se encuentra con vida y tiene 2 o 3 vecinos exactamente
            tableroActual[i][j]="o";
             textoTemporal="\n * Se mantiene vivo en ("+i+", "+j+") por tener 2 o 3 vecinos ";
            textoGenActual= textoGenActual.concat(textoTemporal);
            }
            default->
            { //si ya se encuentra con vida y tiene menos de 2 vecinos o más de 3
            tableroActual[i][j]="*";
            if(numSeresVivos<2){
             textoTemporal="\n * muere en ("+i+", "+j+") por estar en aislamiento ";
            textoGenActual= textoGenActual.concat(textoTemporal);
            }
            else {
            textoTemporal="\n * muere en ("+i+", "+j+") por estar en hacinamiento ";
            textoGenActual= textoGenActual.concat(textoTemporal);
            }
            }
        }
        }
      else if ("*".equals(tableroAnterior[i][j])){
        switch(numSeresVivos){
            case 3->{
            tableroActual[i][j]="o";
            textoTemporal="\n * Nace en ("+i+", "+j+") Por 3 vecinos ";
            textoGenActual= textoGenActual.concat(textoTemporal);
            }
            default->{
            tableroActual[i][j]="*";
         }
        }   
      }

    }


   

//generar titulo de cada generación, así como capturar los datos de la generación actual y pasarlos al acomulado de las generaciones anteriores
   public void actualizarGenTablaYTexto(){
   textoTableroAcumulado.add("\n NUM_Generación:"+(numeroGenActual+1));
    textoTableroAcumulado.add(textoGenActual);
    // Copiar tableroActual a tableroAnterior
     for (int x = 0; x < FILAS; x++) {
    System.arraycopy(tableroActual[x], 0, tableroAnterior[x], 0, COLUMNAS);
    }
    //limpiar tablero actual 
    textoGenActual=" ";

   }

   /* verificar que el juego continue,se considera si siguen existiendo seres vivos,
   además de verificar que han existido cambios entre la penultima y ultima iteración */
   public boolean  validarContinuar(){
    int serevivos=0;
        for (int i = 0; i < this.FILAS; i++) {
        for (int j = 0; j < this.COLUMNAS; j++) {
            if("o".equals(tableroActual[i][j])){
                serevivos++;
            }
        }
    }
    if(serevivos==0){
        System.out.println("No quedan seres vivos");
        return true;
    }
    if(Arrays.deepEquals(tableroAnterior, tableroActual)){

        System.out.println("No hubieron más cambios");
                    System.out.println(this.toString()); 
             salto.nextLine();
        return true;
    }
    return false;

   }
   /*modificar el arraylist del texto actual para eliminar corchetes y "," */
   public String eliminarComas(ArrayList<String> texto){
    String textoOriginal=texto.toString();
    String textoSinComas=textoOriginal.replace(",", "").replace("[", "").replace("]", "");
    return textoSinComas;
   }

   /*Recibir datos String enteros y realizar la separaciones aquí, además de las asignaciones de variables */
   public boolean  recibirEntradaPosiciones(String entrada){

  String[] entradaSeparada=entrada.split(",");  //se separa la entrada y se guarda en un arreglo tipo String
                   int valorTemporal;
                   try {
            for (int i = 1; i < entradaSeparada.length; i++) {
                valorTemporal=Integer.parseInt(entradaSeparada[i]);
                     if(i %2 ==0){
                     getY().add(valorTemporal);
                      }
                       else{
                        getX().add(valorTemporal);
                       }
   }
return true;
}
catch (NumberFormatException e){
    System.err.println("Error al tratar de convertir a int");
    return false;
}
catch (ArrayIndexOutOfBoundsException e){
    System.err.println("Faltan coordenadas en la entrada");
    return false;
}
   }
//contar la cantidad de datos dentro del sting para determinar coordendas y aplicar en validacion
   public int regresarContador(String [] entradaSeparada){
    int contador=0;
     for (int i = 1; i < entradaSeparada.length; i++) {
     contador++;
     }
     return contador;
   }
   
   //Función de inicio que acomula las funciones principales
   public void iniciarlizar(String entrada){
    boolean validar;
    validar=recibirEntradaPosiciones(entrada);
    if(validar){
           generarPrimeraGen();
           ejecutarJuego2();
           }
   }

/*Se agrega el titulo de Resumen de juego, además de que se imprime cada nueva generación, 
 * mientras las generaciones no se acaben o la función validarcontinuar no regrese un true. Una vez finaliza
 * se arroja el resumen del juego.
 */
public void ejecutarJuego2(){ //cambiar por do while, para no usar breaks
    int g=0;
    do{
     //necesario para generar nueva generación
            System.out.println(this.toString()); 
           // System.out.println(eliminarComas(getTextoActual()));      //En el caso de que se requiera mostrar cada generación 
             salto.nextLine();
        calculoNumeroVecinos(); 
        numeroGenActual++; 
        g++;
}
while (g <= NUMERODEGENERACIONES-1 || !validarContinuar());
//mostrar el resumen al terminar siempre
System.out.println(eliminarComas(getTextoAcumulado()));
actualizarGenTablaYTexto();

}
}
