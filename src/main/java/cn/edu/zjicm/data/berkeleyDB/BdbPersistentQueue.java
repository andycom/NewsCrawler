package cn.edu.zjicm.data.berkeleyDB;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
  
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseExistsException;
import com.sleepycat.je.DatabaseNotFoundException;
import com.sleepycat.je.EnvironmentConfig;
/**
 * �־û�����,����BDBʵ��,Ҳ�̳�Queue,�Լ��������л�.������ͬ��Queue��ʱ,����ʹ�ú���Ҫ�ر�
 * ���һ����ڴ�Queue,����ͻ�ȡֵ��Ҫ������һ����ʱ��
 * ����Ϊʲô�Ǽ̳�AbstractQueue������ʵ��Queue�ӿ�,����ΪֻҪʵ��offer,peek,poll������������,
 * ������remove,addAll,AbstractQueue������⼸������ȥʵ��
 * 
 * @contributor guoyun
 * @param <E>
 */
public class BdbPersistentQueue<E extends Serializable> extends AbstractQueue<E> implements
        Serializable {
    private static final long serialVersionUID = 3427799316155220967L;
    private transient BdbEnvironment dbEnv;            // ���ݿ⻷��,�������л�
    private transient Database queueDb;             // ���ݿ�,���ڱ���ֵ,ʹ��֧�ֶ��г־û�,�������л�
    private transient StoredMap<Long,E> queueMap;   // �־û�Map,KeyΪָ��λ��,ValueΪֵ,�������л�
    private transient String dbDir;                 // ���ݿ�����Ŀ¼
    private transient String dbName;				// ���ݿ�����
    private AtomicLong headIndex;                   // ͷ��ָ��
    private AtomicLong tailIndex;                   // β��ָ��
    private transient E peekItem=null;              // ��ǰ��ȡ��ֵ
    
    /**
     * ���캯��,����BDB���ݿ�
     * 
     * @param db
     * @param valueClass
     * @param classCatalog
     */
    public BdbPersistentQueue(Database db,Class<E> valueClass,StoredClassCatalog classCatalog){
        this.queueDb=db;
        this.dbName=db.getDatabaseName();
        System.out.println("-----------���ݿ�����dbName"+dbName);
        headIndex=new AtomicLong(0);
        tailIndex=new AtomicLong(0);
        bindDatabase(queueDb,valueClass,classCatalog);
    }
    /**
     * ���캯��,����BDB���ݿ�λ�ú�����,�Լ��������ݿ�
     * 
     * @param dbDir
     * @param dbName
     * @param valueClass
     */
    public BdbPersistentQueue(String dbDir,String dbName,Class<E> valueClass){
        headIndex=new AtomicLong(0);
        tailIndex=new AtomicLong(0);
        this.dbDir=dbDir;
        this.dbName=dbName;
//        System.out.println("-----------���ݿ�·����dbDir"+dbName);
        createAndBindDatabase(dbDir,dbName,valueClass);
    }
    /**
     * �����ݿ�
     * 
     * @param db
     * @param valueClass
     * @param classCatalog
     */
    public void bindDatabase(Database db, Class<E> valueClass, StoredClassCatalog classCatalog){
        EntryBinding<E> valueBinding = TupleBinding.getPrimitiveBinding(valueClass);
        if(valueBinding == null) {
            valueBinding = new SerialBinding<E>(classCatalog, valueClass);   // ���л���
        }
        queueDb = db;
        queueMap = new StoredSortedMap<Long,E>(
                db,                                             // db
                TupleBinding.getPrimitiveBinding(Long.class),   //Key
                valueBinding,                                   // Value
                true);                                          // allow write
    }
    /**
     * �����Լ������ݿ�
     * 
     * @param dbDir
     * @param dbName
     * @param valueClass
     * @throws DatabaseNotFoundException
     * @throws DatabaseExistsException
     * @throws DatabaseException
     * @throws IllegalArgumentException
     */
    private void createAndBindDatabase(String dbDir, String dbName,Class<E> valueClass) throws DatabaseNotFoundException,
    DatabaseExistsException,DatabaseException,IllegalArgumentException{
        File envFile = null;
        EnvironmentConfig envConfig = null;
        DatabaseConfig dbConfig = null;
        Database db=null;
 
        try {
            // ���ݿ�λ��
            envFile = new File(dbDir);
            System.out.println("-----------�ݿ�λ�ã�dbDir"+dbDir);
            // ���ݿ⻷������
            envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setTransactional(false);
            System.out.println("-----------���ݿ⻷���������");
            // ���ݿ�����
            dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setTransactional(false);
            dbConfig.setDeferredWrite(true);
            System.out.println("-----------���ݿ����ã�3::"+envFile);
            // ��������
            dbEnv = new BdbEnvironment(envFile, envConfig);
            System.out.println("-----------�������ݿ⻷����4::"+envConfig);
            // �����ݿ�
            db = dbEnv.openDatabase(null, dbName, dbConfig);
            System.out.println("-----------�����ݿ⣺5 ::"+dbName);
            // �����ݿ�
            bindDatabase(db,valueClass,dbEnv.getClassCatalog());
            System.out.println("-----------�����ݿ�6"+db+valueClass);
            
            List myDbNames = dbEnv.getDatabaseNames();  
            System.out.println("Database size: " + myDbNames.size());  
            for (int i = 0; i < myDbNames.size(); i++) {  
                System.out.println("Database Name: " + (String) myDbNames.get(i));  
            }  
           
             
        } catch (DatabaseNotFoundException e) {
            throw e;
        } catch (DatabaseExistsException e) {
        	 System.out.println("����1"+e);
            throw e;
           
        } catch (DatabaseException e) {
        	System.out.println("����2"+e+envFile);
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
 
        
    }
    
    /**
     * ֵ������
     */
    @Override
    public Iterator<E> iterator() {
    	System.out.print("�������ñ���ֵ");
        return queueMap.values().iterator();
        
    }
    /**
     * ��С
     */
    @Override
    public int size() {
        synchronized(tailIndex){
            synchronized(headIndex){
                return (int)(tailIndex.get()-headIndex.get());
            }
        }
    }
    
    /**
     * ����ֵ
     */
    @Override
    public boolean offer(E e) {
        synchronized(tailIndex){
            queueMap.put(tailIndex.getAndIncrement(), e);   // ��β������
        }
        return true;
    }
    
    /**
     * ��ȡֵ,��ͷ����ȡ
     */
    @Override
    public E peek() {
        synchronized(headIndex){
            if(peekItem!=null){
                return peekItem;
            }
            E headItem=null;
            while(headItem==null&&headIndex.get()<tailIndex.get()){ // û�г�����Χ
                headItem=queueMap.get(headIndex.get());
                if(headItem!=null){
                    peekItem=headItem;
                    continue;
                } 
                headIndex.incrementAndGet();    // ͷ��ָ�����
            }
            return headItem;
        }
    }
    
    /**
     * �Ƴ�Ԫ��,�Ƴ�ͷ��Ԫ��
     */
    @Override
    public E poll() {
        synchronized(headIndex){
            E headItem=peek();
            if(headItem!=null){
                queueMap.remove(headIndex.getAndIncrement());
                peekItem=null;
                return headItem;
            }
        }
        return null;
    }
    
    /**
     * �ر�,Ҳ���ǹر������õ�BDB���ݿ⵫���ر����ݿ⻷��
     */
    public void close(){
        try {
            if(queueDb!=null){
                queueDb.sync();
                queueDb.close();
            }
        } catch (DatabaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * ����,��������ݿ�,����ɾ�����ݿ�����Ŀ¼,����.����뱣������,�����close()
     */
    @Override
    public void clear() {
       try {
    	   close();
    	   if(dbEnv!=null&&queueDb!=null){
				dbEnv.removeDatabase(null, dbName==null?queueDb.getDatabaseName():dbName); 
                dbEnv.close();
           }
	    } catch (DatabaseNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (DatabaseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } finally{
	    	try {
	    		if(this.dbDir!=null){
	    			FileUtils.deleteDirectory(new File(this.dbDir));
	    		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
    }
    
}
