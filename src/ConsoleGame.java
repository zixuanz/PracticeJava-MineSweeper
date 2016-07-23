import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleGame extends core{
	
	private static boolean start = true;					//whether start a new game or not
	
	//directly start a game
	ConsoleGame() throws IOException{
		startGame();
	}

	public void prettyPrint(){
		for(int i=0; i<=map.getMapWidth(); i++){
			System.out.print(i+"\t");
		}
		for(int i=1; i<=map.getMapHeight(); i++){
			System.out.println();
			System.out.print(i + "\t");
			
			for(int j=1; j<=map.getMapWidth(); j++){
				if(getSweep()[i][j] == -1){
					System.out.print(map.getMap()[i][j] + "\t");
				}else{
					System.out.print("*\t");
				}
			}
			
		}
		System.out.println();
		
	}
	
	
	public void startGame() throws IOException{
		int height = 0;
		int width = 0;
		int num = 0;
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
			
			//input for the necessary value from user
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
					height = Integer.parseInt(temp[0]);
					width = Integer.parseInt(temp[1]);
					num = Integer.parseInt(temp[2]);
				}catch(Exception e){
					System.out.println("ERROR: Invalid Input. Input may not be numbers. Please input again!");
					input = "";
				}
			}
			
			System.out.println("\n======================================================================================================\n");
			
			//create game map
			resetGame(height, width, num);
			prettyPrint();
			
			//game start
			while(!isMine()){
				input = "";
				int row = 0;
				int col = 0;
				
				//input the block that user want to sweep
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
					row = Integer.parseInt(temp[0]);
					col = Integer.parseInt(temp[1]);
				}
				
				sweepMine(row, col);
				prettyPrint();
				System.out.println("\n======================================================================================================\n");
				
				if(isWin()){
					System.out.println("\\\\\\\\Congratulations! You win!////");
					break;
				}
			}
			if(isMine()){
				System.out.println("Sorry. You failed.");
				System.out.println("\n======================================================================================================\n");

				System.out.println(">>>Solution<<<");
				printMap();
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
	

	public boolean isValidMap(int height, int width, int num){
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
	

	public boolean isValidAction(int row, int col){
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
