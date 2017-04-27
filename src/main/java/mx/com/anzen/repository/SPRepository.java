package mx.com.anzen.repository;

import org.springframework.stereotype.Repository;
import mx.com.anzen.bean.ParametrosSP;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Vector;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * 
 * @author: Angel
 * @category: Persistencia
 * @version: 1.0 (21/02/17) 
 */
@Repository
@Transactional
public class SPRepository {
	
	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;
	
	  
	  /**
	   * Invoke a Stored Procedure (Consultas)
	   * 
	   * Tipos de Datos de PARAMETROS
	   * 1.- Enteros
	   * 2.- Decimales
	   * 3.- Texto
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	  public Object getQuerySP(String nameSP, List<ParametrosSP> parametros){
		  
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  Integer parametroEntero = 0;
		  Float parametroDecimal = 0F;
		  String parametroTexto = "";
		  
		  // Constantes
		  final int ENTEROS = 1, DECIMALES=2, TEXTO=3;
		  
		  try{
			  // Se invoca el SP
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);
			  
			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case ENTEROS:
			  			parametroEntero = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroEntero);
			  			break;
			  		
			  		case DECIMALES:
			  			parametroDecimal = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDecimal);
			  			break;
			  			
			  		case TEXTO:
			  			parametroTexto = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroTexto);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			  
			  // execute SP
			  storedProcedure.execute();
			  
			  
		  } catch(Exception ex){
			  System.out.println(ex.getMessage());
		  }
		  
		  return storedProcedure.getResultList();
	  }
	  
	  
	  
	  
	  /**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * Tipos de Datos de PARAMETROS
	   * 1.- Enteros
	   * 2.- Decimales
	   * 3.- Texto
	   * 
	   * @param nameSP: Nombre del Stored Procedure que sera invocado en la base de datos
	   * @param parametros: Se envia una lista con los parametros de entrada que seran registrados en el SP en un objeto lista 
	   *                    con los atributos: Tipo de dato, nombre del parametro y el valor del parametro
	   *                    
	   * @return Object: Retorna un objeto generico para ser casteado al tipo de entidad que se requiera
	   */
	  public Object getCRUDSP(String nameSP, List<ParametrosSP> parametros){
		
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  Integer parametroEntero = 0;
		  Float parametroDecimal = 0F;
		  String parametroTexto = "";
		  Integer reslt = null;
		  String message = null;
		  Vector<Object> response = new Vector<Object>();
		  
		  // Constantes
		  final int ENTEROS = 1, DECIMALES=2, TEXTO=3;
		  
		  try{
			  // Se invoca el SP
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);
			  
			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros de Entrada
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case ENTEROS:
			  			parametroEntero = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroEntero);
			  			break;
			  		
			  		case DECIMALES:
			  			parametroDecimal = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDecimal);
			  			break;
			  			
			  		case TEXTO:
			  			parametroTexto = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroTexto);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			  
			  // Parametros de salida
			  storedProcedure.registerStoredProcedureParameter("reslt", Integer.class, ParameterMode.OUT);
			  storedProcedure.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
			  
			  // execute SP
			  storedProcedure.execute();
			  
		      // get the result
			  reslt = (Integer)storedProcedure.getOutputParameterValue("reslt");
			  message = (String)storedProcedure.getOutputParameterValue("message");
			  
			  response.add(reslt);
			  response.add(message);
			  
			  
			  
		  } catch(Exception ex){
			  response.add(-1);
			  response.add(ex.getMessage().toString());
			  
		  }
		  
		  return response;
	  }
}

