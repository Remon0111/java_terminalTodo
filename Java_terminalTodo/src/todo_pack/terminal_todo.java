package todo_pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class terminal_todo {

	public static void main(String[] args) {
		// 初期化
		Connection dbResult = null;
		try {
			// dbの情報
			// "jdbc:postgresql:/"←ここは固定 
			// "/localhost:5432"←初期設定から変えてなければ同じ 
			// "todo_db"←ここは作ったテーブルの名前
			String dbUrl = "jdbc:postgresql://localhost:5432/todo_db";
			String dbUserName = "postgres";
			String dbPassword = "postgres";
			System.out.println("接続開始");

			// 接続
			dbResult = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("接続できました。");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dbResult != null) {
					dbResult.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
