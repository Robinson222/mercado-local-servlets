package co.edu.sena.mercadolocal.service;
import static org.junit.Assert.*;
import co.edu.sena.mercadolocal.model.Product;
import co.edu.sena.mercadolocal.repository.ProductRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
/** Service tests use an in-memory repository, so they do not need MySQL. */
public class ProductServiceTest {
 private MemoryRepository repository; private ProductService service;
 @Before public void setUp(){repository=new MemoryRepository();service=new ProductService(repository);}
 @Test public void createsAndTrimsProductName()throws SQLException{
  Product saved=service.create("  Cafe  ","Producto local","32000.00","5");
  assertEquals("Cafe",saved.getName());assertEquals(1L,saved.getId());assertEquals(1,service.findAll().size());
 }
 @Test public void rejectsInvalidPriceBeforeSaving()throws SQLException{
  try{service.create("Cafe","Producto local","precio","5");fail("Debio rechazar el precio.");}
  catch(IllegalArgumentException expected){assertTrue(expected.getMessage().contains("formato"));}
  assertTrue(repository.products.isEmpty());
 }
 @Test public void updatesAndDeletesProduct()throws SQLException{
  Product saved=service.create("Cafe","Producto local","32000.00","5");
  assertTrue(service.update("1","Cafe premium","Producto local","35000.00","4"));
  assertEquals("Cafe premium",service.findAll().get(0).getName());
  assertTrue(service.delete("1"));assertFalse(service.delete("1"));
 }
 private static final class MemoryRepository implements ProductRepository {
  private final List<Product> products=new ArrayList<>();private long nextId=1;
  @Override public List<Product> findAll(){return new ArrayList<>(products);}
  @Override public Product insert(Product p){Product saved=new Product(nextId++,p.getName(),p.getDescription(),p.getPrice(),p.getStock());products.add(saved);return saved;}
  @Override public boolean update(Product p){for(int i=0;i<products.size();i++)if(products.get(i).getId()==p.getId()){products.set(i,p);return true;}return false;}
  @Override public boolean delete(long id){return products.removeIf(p->p.getId()==id);}
 }
}
