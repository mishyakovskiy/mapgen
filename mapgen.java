import java.util.Random;

public class mapgen
{
	private static long seed;
	private static int map_len = 128;
	private static char[][] map = new char[map_len][map_len];
	private static MAP_TYPE mp;
	public static void main(String[] args)
	{
		System.out.println("Выберите сид для своей игры.");
		seed = Long.parseLong(System.console().readLine());
		Random r = new Random(seed);
		System.out.println("Выберите тип земли:\n 1. Поле.\n2. Озеро.\n3. Гористая местность.\n4. Остров.\n5. Дельта.\n6. Река.");
		int t = Integer.parseInt(System.console().readLine());
		switch (t)
		{
			case 1:
				mp = MAP_TYPE.FIELD;
				break;
			case 2:
				mp = MAP_TYPE.LAKE;
				break;
			case 3:
				mp = MAP_TYPE.MOUNTAINS;
				break;
			case 4:
				mp = MAP_TYPE.ISLAND;
				break;
			case 5:
				mp = MAP_TYPE.RDELTA;
				break;
			case 6:
				mp = MAP_TYPE.RIVER;
				break;
			default:
				break;
		}
		System.out.println("Начинаем генерацию карты!...");
		if(genGround(r) == true)
		{
			System.out.println("Готово!");
		} else {
			System.out.println("Произошла ошибка во время генерации карты!");
		}
		render();
	}
	private static boolean genGround(Random r)
	{
		for(int y = 0; y < map_len; y++)
		{
			for(int x = 0; x < map_len; x++)
			{
				map[y][x] = '0';
			}
		}
		System.out.println("Создаем рельеф...");
		if(mp == MAP_TYPE.FIELD)
		{
			for(int y = 0; y < map_len; y++)
			{
				for(int x = 0; x < map_len; x++)
				{
					if(r.nextFloat() <= 0.2)
					{
						map[y][x] = '.';
					} else
					{
						map[y][x] = '_';
					}
				}
			}
			return true;
		}
		if(mp == MAP_TYPE.LAKE)
		{
			int xx = map_len/2;
			int yy = map_len/2;
			for(int y = 0; y < map_len; y++)
			{
				for(int x = 0; x < map_len; x++)
				{
					float t = r.nextFloat();
					if(t <= 0.3)
					{
						map[y][x] = '.';
					}
					else
					{
						map[y][x] = '_';
					}

					if(t <= 0.15)
					{
						switch(r.nextInt(3))
						{
							case 0:
							map[y][x] = 'T';
							break;
							case 1:
							map[y][x] = 'q';
							break;
							case 2:
							map[y][x] = 'P';
							break;
						}
					}
				}
			}
			while(xx < map_len && yy < map_len)
			{
				map[yy][xx] = '≈';
				switch (r.nextInt(-2, 2))
				{
					case -1:
					if(xx != 0) xx--;
					break;
					case 0:
					break;
					case 1:
					xx++;
					break;
				}
				switch (r.nextInt(-2, 2))
				{
					case -1:
					if(yy != 0) yy--;
					break;
					case 0:
					break;
					case 1:
					yy++;
					break;
				}
			}
			return true;
		}
		return false;
	}
	private static void render()
	{
		System.out.println();
		for(int y = 0; y < map_len; y++)
		{
			for(int x = 0; x < map_len; x++)
			{
				if(map[y][x] == '_' || map[y][x] == 'T') System.out.print(COLOURS.GREEN.getCode());
				if(map[y][x] == 'q') System.out.print(COLOURS.YELLOW.getCode());
				if(map[y][x] == 'P') System.out.print(COLOURS.PURPLE.getCode());
				if(map[y][x] == '.') System.out.print(COLOURS.COMMON.getCode());
				if(map[y][x] == '≈') System.out.print(COLOURS.LIGHTBLUE.getCode());
				if(map[y][x] == '#') System.out.print(COLOURS.YELLOW.getCode());
				System.out.print(map[y][x]);
			}
			System.out.println();
		}
	}
	
}

enum MAP_TYPE
{
	FIELD,
	LAKE,
	MOUNTAINS,
	ISLAND,
	RDELTA,
	RIVER
}

enum COLOURS
{
	RED("\033[31m"),
	GREEN("\033[32m"),
	YELLOW("\033[33m"),
	BLUE("\033[34m"),
	PURPLE("\033[35m"),
	LIGHTBLUE("\033[36m"),
	COMMON("\033[0m");

	private String code;

	COLOURS(String code)
	{
		this.code = code;
	}
	public String getCode()
	{
		return code;
	}
}

