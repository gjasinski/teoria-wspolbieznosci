package tw04.task1;

import java.util.concurrent.locks.Lock;

public class Buffer {
	private final static int NONE_TYPE = -1;
	private final static int PRODUCENT_TYPE = -2;
	private final static int CONSUMENT_TYPE = -3;
	private final Lock[] buffer;
	private final int[] previousThreadType;
	private final int bufferSize;
	
	Buffer(int bufferSize){
		this.bufferSize = bufferSize;
		this.buffer = new Lock[bufferSize];
		this.previousThreadType = new int[bufferSize];
	}
	
	private void initializePreviousThreadType(){
		for(int i = 0; i < bufferSize; i++){
			previousThreadType[i] = NONE_TYPE;
		}
	}
	
	void getResourceProducent(int resourceId){
		if(resourceId > this.bufferSize){
			throw new IndexOutOfBoundsException();
		}
		Lock currentLock = this.buffer[resourceId];
		while(previousThreadType[resourceId] != NONE_TYPE){
		}
		currentLock.lock();
		previousThreadType[resourceId] = PRODUCENT_TYPE; 
		unlockPreviousResource(resourceId);
	}
	
	void getResourceProcessing(int resourceId, int processingId){
		if(resourceId > this.bufferSize){
			throw new IndexOutOfBoundsException();
		}
		Lock currentLock = this.buffer[resourceId];
		currentLock.lock();
		unlockPreviousResource(resourceId);
	}
	
	void waitUntilPreviousProcessEndProcessing(int resourceId, int processingId){
		if(processingId == 0){
			waitUntilPreviousProcessEndProcessingdFirstProcess(resourceId, processingId);
		}
		else{
			waitUntilPreviousProcessEndProcessingNotFirstProcess(resourceId, processingId);
		}
	}
	
	
	private void waitUntilPreviousProcessEndProcessingNotFirstProcess(int resourceId, int processingId) {
		while(previousThreadType[resourceId] != (processingId-1)){
			
		}
		
	}

	private void waitUntilPreviousProcessEndProcessingdFirstProcess(int resourceId, int processingId) {
		while(previousThreadType[resourceId] != PRODUCENT_TYPE){
			
		}
	}

	private void unlockPreviousResource(int currentResourceId){
		if(currentResourceId != 0) {
			Lock previousLock = this.buffer[currentResourceId - 1];
			previousLock.unlock();
		}
	}
}
