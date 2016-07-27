
public class core {
	
	protected MineMap map;									//load map
	private boolean mine;									//indicate there is mine or not
	private int result;										//remained blocks
	private int sweep[][];									//record which is sweep
	
	public int[][] getSweep(){
		return sweep;
	}

	public void resetGame(int height, int width, int num){
		map = new MineMap(height, width, num);
		result = height * width;
		mine = false;
		sweep = new int[height+2][width+2];
	}
	
	//user sweeps a block and check the cases
	public void sweepMine(int row, int col){
		
		if(map.getMap()[row][col] == 'X'){
			//when there is mine, game is failed   -------case for failure
			mine = true;
			
		}else if(map.getMap()[row][col] == 0){
			//open the available block when there is nothing in current block
			
			zeroSweep(row, col);
			
			/*        bad idea, and bad result. but good for thinking
			int left = col;
			int right = col;
			int top = row;
			int bottom = row;
			int topEnd = 0;
			int bottomEnd = 0;
			while(left>0){
				top = row;
				bottom = row;
				topEnd = 0;
				bottomEnd = 0;
				while(top>0 && bottom<=map.getMapHeight()){
					if(map.getMap()[top][left] == 0 && sweep[top][left] == 0){
						sweep[top][left] = -1;
						top--;
						result--;
					}else{
						topEnd = 1;
					}
					if(map.getMap()[bottom][left] == 0 && sweep[bottom][left] == 0){
						sweep[bottom][left] = -1;
						bottom++;
						result--;
					}else{
						bottomEnd = 1;
					}
					if(bottomEnd + topEnd == 2){
						break;
					}
				}
				left--;
			}
			while(right<=map.getMapWidth()){
				top = col;
				bottom = col;
				right++;
				topEnd = 0;
				bottomEnd = 0;
				while(top>0 && bottom<=map.getMapHeight()){
					if(map.getMap()[top][right] == 0 && sweep[top][right] == 0){
						sweep[top][right] = -1;
						top--;
						result--;
					}else{
						topEnd = 1;
					}
					if(map.getMap()[bottom][right] == 0 && sweep[bottom][right] == 0){
						sweep[bottom][right] = -1;
						bottom++;
						result--;
					}else{
						bottomEnd = 1;
					}
					if(bottomEnd + topEnd == 2){
						break;
					}
					
				}
			}
			*/

		}else{
			sweep[row][col] = -1;
			result--;
		}
	}
	
	//the connected zero blocks marked with OPEN when the sweep on blocks with 0
	private void zeroSweep(int row, int col){
		//sweep all the concatenated zero blocks
		for(int i=row-1; i<=row+1; i++){
			for(int j=col-1; j<=col+1; j++){
				//make sure in the available area
				if(i>0 && i<=map.getMapHeight() && j>0 && j<=map.getMapWidth()){
					if(map.getMap()[i][j] == 0 && sweep[i][j] == 0){
						sweep[i][j] = -1;
						result--;
						zeroSweep(i, j);
					}else if(map.getMap()[i][j] != 0 && sweep[i][j] == 0){
						sweep[i][j] = -1;
						result--;
					}
				}
				
			}
		}
	}
	
	
	//both check whether win or not.
	public boolean isWin(){
		if(result == map.getMineNum())
			//when all mine blocks are not sweep, win the game ------case for win
			return true;
		else
			return false;
	}
	
	public boolean isMine(){
		return this.mine;
	}
	
	public void printMap(){
		map.prettyPrint();
	}
	
	public void printSweep(){
		for(int i=0; i<=map.getMapHeight(); i++){
			for(int j=0; j<=map.getMapWidth(); j++){
				System.out.print(getSweep()[i][j] + " ");
			}
			System.out.println();
		}
	}
	
}
