package org.pstcl.ea;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Component
public class FileService implements InitializingBean {

	@Autowired
	FileMasterEntityRepository entityRepository;

	@Autowired
	FileMasterEntityRepository2 fileMaster2Repo;


	@Autowired
	FileMasterEntityRepository3 dupfileMaster3Repo;
	
	public void afterPropertiesSet() throws Exception {
		//cleanFiles();
		copyFilesToNewDb();
	}

	//	public void copyFilesToNewDb1()
	//	{
	//		Iterable<FileMaster> fileMasters=entityRepository.findAll();
	//		for (FileMaster fileMaster : fileMasters) 
	//		{
	//			FileMaster2 fileMaster2=new FileMaster2(fileMaster);
	//			try {
	//
	//				fileMaster2Repo.save(fileMaster2);
	//			}
	//			catch(DuplicateKeyException duplicateKeyException)
	//			{
	//				duplicateKeyException.printStackTrace();
	//				
	//				handleduplicates(fileMaster);
	//			}
	//			catch (Exception e)
	//			{
	//				e.printStackTrace();
	//
	//				handleduplicates(fileMaster);
	//			
	//			}
	//
	//			
	//		}
	//	}
	//	
	//	private void handleduplicates(FileMaster fileMaster)
	//	{
	//		FileMaster2 fileMaster2=new FileMaster2(fileMaster);
	//		List<FileMaster> duplicateList=entityRepository.findAllByTransactionDateAndMeter(fileMaster.getTransactionDate(),fileMaster.getMeter());
	//		if(duplicateList.size()>1)
	//		{
	//			for (FileMaster duplicate : duplicateList) {
	//				dupfileMaster3Repo.save(new FileMaster3(duplicate));
	//				if(fileMaster.getTxnId()<duplicate.getTxnId())
	//				{
	//					fileMaster2=new FileMaster2(duplicate);
	//				}
	//			}
	//			fileMaster2Repo.save(fileMaster2);
	//			
	//		}
	//
	//	}
	//	

	public void copyFilesToNewDb()
	{
		Iterable<FileMaster> fileMasters=entityRepository.findAll();
		for (FileMaster fileMaster : fileMasters) 
		{
			//FileMaster2 fileMaster2=new FileMaster2(fileMaster);
			List<FileMaster> duplicateList=entityRepository.findAllByTransactionDateAndMeter(fileMaster.getTransactionDate(),fileMaster.getMeter());
			if(duplicateList.size()==1)
			{
				//fileMaster2=new FileMaster2(duplicateList.get(0));
			}
			else
			{
				for (FileMaster duplicate : duplicateList) {
					dupfileMaster3Repo.save(new FileMaster3(duplicate));
//					if(fileMaster2.getTxnId()<duplicate.getTxnId())
//					{
//						fileMaster2=new FileMaster2(duplicate);
//					}
				}

			}
			try {

//				fileMaster2Repo.save(fileMaster2);
			}
			catch(DuplicateKeyException duplicateKeyException)
			{
				duplicateKeyException.printStackTrace();				
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}

		}
	}
}
