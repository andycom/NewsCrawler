package cn.edu.zjicm.data.berkeleyDB;

import java.io.File;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
/**
 * BDB���ݿ⻷��,���Ի���StoredClassCatalog������
 * 
 * @contributor guoyun
 */
public class BdbEnvironment extends Environment {
    StoredClassCatalog classCatalog; 
    Database classCatalogDB;
    
    /**
     * Constructor
     * 
     * @param envHome ���ݿ⻷��Ŀ¼
     * @param envConfig config options  ���ݿ⻻���������
     * @throws DatabaseException
     */
    public BdbEnvironment(File envHome, EnvironmentConfig envConfig) throws DatabaseException {
        super(envHome, envConfig);
    }
 
    /**
     * ����StoredClassCatalog
     * @return the cached class catalog
     */
    public StoredClassCatalog getClassCatalog() {
        if(classCatalog == null) {
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            try {
                classCatalogDB = openDatabase(null, "classCatalog", dbConfig);
                classCatalog = new StoredClassCatalog(classCatalogDB);
            } catch (DatabaseException e) {
                // TODO Auto-generated catch block
                throw new RuntimeException(e);
            }
        }
        return classCatalog;
    }
 
    @Override
    public synchronized void close() throws DatabaseException {
        if(classCatalogDB!=null) {
            classCatalogDB.close();
        }
        super.close();
    }
 
}