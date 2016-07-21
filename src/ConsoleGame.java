import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleGame {
	
	private MineMap map;									//load map
	private int result;										//rest blocks
	private boolean mine;									//record win or not
	private int sweep[][];									//record which is sweep
	private static boolean start = true;					//whether start a new game or not
	
	//directly start a game
	ConsoleGame() throws IOException{
		startGame();
	}
	
	
	ConsoleGame(short height, short width, short num){
		resetGame(height, width, num);
	}

	public boolean getMine(){
		return this.mine;
	}
	
	public void resetGame(short height, short width, short num){
		map = new MineMap(height, width, num);
		result = height * width;
		mine = false;
		sweep = new int[height+2][width+2];
		setSweep();
	}
	
	
	//user sweeps a block and check the cases
	public void sweepMine(int row, int col){
		
		if(map.getMap()[row][col] == 'O'){
			//when there is mine, game is failed   -------case for failure
			mine = true;
			
		}else if(map.getMap()[row][col] == 0){
			//open the available block when there is nothing in current block
			
			zeroSweep(row, col);
			
			/*        bad idea. but good for thinking
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
	
	private void setSweep(){
		for(int i=1; i<map.getMapHeight() + 2; i++)
			sweep[i][0] = i;
		for(int i=1; i<map.getMapWidth() + 2; i++)
			sweep[0][i] = i;
		for(int i=1; i<map.getMapHeight() + 2; i++){
			for(int j=1; j<map.getMapWidth() + 1; j++){
				sweep[i][j] = 0;
			}
		}
	}
	
	public boolean isWin(){
		if(result == map.getMineNum())
			//when all mine blocks are not sweep, win the game ------case for win
			return true;
		else
			return false;
	}
	
	
	public void prettyPrint(){
		
		for(int i=0; i<=map.getMapHeight(); i++){
			for(int j=0; j<=map.getMapWidth(); j++){
				
				if(sweep[i][j] == -1){
					System.out.print(map.getMap()[i][j] + "\t");
				}else if(sweep[i][j]==0 && i!=0 && j!=0){		//avoid the first 0 used to indicate the index is hidden
					System.out.print("*\t");
				}else{
					System.out.print(sweep[i][j] + "\t");
				}
				
			}
			System.out.println();
		}
		
		//map.prettyPrint();
	}
	
	private void printSweep(){
		for(int i=0; i<=map.getMapHeight(); i++){
			for(int j=0; j<=map.getMapWidth(); j++){
				System.out.print(sweep[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void startGame() throws IOException{
		
		short height = 0;
		short width = 0;
		short num = 0;
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		System.out.println("======================================================================================================");
		System.out.println(" How to play:");
		System.out.println(" >To start game, please input the HEIGHT and WIDTH of map and the NUM of MINES in this map sequencely.");
		System.out.println(" >The range for WIDTH and HEIGHT is 1 ~ 99");
		System.out.println("  Eg. 4 3 2. It indicates that system will create a 4x3 map with 2 mines.");
		System.out.println(" >After input, type Return on key board, the map will be printed on screen. "
				+ "				\n X represents unsweeped block. * represents mine. And numbers represent how many mines in 3x3 area.");
		System.out.println("  Eg. XX1\n     X*X\n     XXX");
		System.out.println(" >Each time input the row and col number of the block that you want to sweep.");
		System.out.println(" >When only the blocks with mine left in the map, WIN the game. /n Enjoy!/n/n");
		System.out.println("======================================================================================================");
		
		while(start){
			start = false;
			input = "";
			while(input.length() == 0 || !isValidMap(height, width, num)){
				
				System.out.println("Please input height and width of map and number of mines here:");
				input = buffer.readLine();
				
				String temp[] = input.split(" ");
				
				if (temp.length > 3){
					System.out.println("ERROR: Input too many values. Please input again!");
					input = "";
					continue;
				}else if(temp.length < 3){
					System.out.println("ERROR: Input less values. Please input again!");
					input = "";
					continue;
				}
				try{
					height = Short.parseShort(temp[0]);
					width = Short.parseShort(temp[1]);
					num = Short.parseShort(temp[2]);
				}catch(NumberFormatException e){
					System.out.println("ERROR: Invalid Input. Input may not be numbers. Please input again!");
					input = "";
				}
			}
			
			System.out.println("\n======================================================================================================\n");
			
			resetGame(height, width, num);
			prettyPrint();
			
			while(!mine){
				input = "";
				short row = 0;
				short col = 0;
				while(input.length() == 0 || !isValidAction(row, col)){
					
					System.out.println("Please input the row and colomn of map that you want to sweep:");
					input = buffer.readLine();
					
					String temp[] = input.split(" ");
					
					if (temp.length > 2){
						System.out.println("ERROR: Input too many values. Please input again!");
						input = "";
						continue;
					}else if(temp.length < 2){
						System.out.println("ERROR: Input less values. Please input again!");
						input = "";
						continue;
					}
					row = Short.parseShort(temp[0]);
					col = Short.parseShort(temp[1]);
				}
				
				sweepMine(row, col);
				prettyPrint();
				System.out.println("\n======================================================================================================\n");
				
				if(isWin()){
					System.out.println("\\\\\\\\Congratulations! You win!////");
					break;
				}
			}
			if(getMine()){
				System.out.println("Sorry. You failed.");
			}
			
			System.out.println("\n======================================================================================================\n");
			
			input = "";
			while(input.length() == 0){
				
				System.out.println("Do you want to start a new game? (Y/N)");
				input = buffer.readLine();
				
				String temp[] = input.split(" ");
				
				if (temp.length > 1){
					System.out.println("ERROR: Input too many values. Please input again!");
					input = "";
					continue;
				}
				
				if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("yes")){
					start = true;
				}else if(input.equalsIgnoreCase("N") || input.equalsIgnoreCase("no")){
					start = false;
				}else{
					System.out.println("ERROR: Invalid Input. End Game!");
				}
			}
			
		}
	}
	

	public boolean isValidMap(short height, short width, short num){
		boolean flag = true;
		if(height<0 || height>99){
			System.out.println("ERROR: The value of height is invalid. Please input height in 0 and 99!");
			flag = false;
		}
		if(width<0 || width>99){
			System.out.println("ERROR: The value of width is invalid. Please input height in 0 and 99!");
			flag = false;
		}
		if((num > width * height | num <= 0) && flag){
			System.out.println("ERROR: The value of number is invalid. Please input available Mine numbers!");
			flag = false;
		}
		return flag;
	}
	

	public boolean isValidAction(short row, short col){
		boolean flag = true;
		if(row<0 || row>map.getMapHeight()){
			System.out.println("ERROR: The value of row is not valid. Please input row in 0 and " + map.getMapHeight() + "!");
			flag = false;
		}
		if(col<0 || col>map.getMapWidth()){
			System.out.println("ERROR: The value of width is not valid. Please input height in 0 and " + map.getMapWidth() + "99!");
			flag = false;
		}
		
		return flag;
	}

}
