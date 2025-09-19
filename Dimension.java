public class Dimension {
    private final int  FILAS;
    private final int  COLUMNAS;
   private final int  GENERACIONES;

   public int getFilas(){
    return FILAS;   
   }
      public int getColumnas(){
    return COLUMNAS;   
   }
      public int getGeneraciones(){
    return GENERACIONES;   
   }

    public Dimension(int filas, int columnas, int generaciones) {
        this.FILAS = filas;
        this.COLUMNAS = columnas;
        this.GENERACIONES = generaciones;
    }

   
    
}
