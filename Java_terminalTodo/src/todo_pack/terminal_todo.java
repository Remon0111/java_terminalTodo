package todo_pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class terminal_todo {

	public static void main(String[] args) {
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rset = null;

		// dbの情報
		// "jdbc:postgresql:/"←ここは固定 
		// "/localhost:5432"←初期設定から変えてなければ同じ 
		// "todo_db"←ここは作ったテーブルの名前
		final String dbUrl = "jdbc:postgresql://localhost:5432/todo_db";
		final String dbUserName = "postgres";
		final String dbPassword = "postgres";
		
		// dbのテーブルの情報
		final String SQL = "insert into todo_db (user_id, title, task_content, deadline, category, task_flag, created_at) VALUES(?, ?, ?, ?, ?, ?, ?)";
		
		// dbの接続
		// 接続の判定を一度nullにする（めっちゃ大事）
		Connection dbResult = null;
		System.out.println("接続開始");
		try (Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword)){ {

			System.out.println("接続できました。");
			
			// dbでオートコミットをオフにする処理
			conn.setAutoCommit(false);
			
			// 特定のコマンドが入力された時の処理
			String input;
			
			Scanner scanner = new Scanner(System.in);
			
			for (;;) {
				System.out.print("command > ");
				input = scanner.next();
				  // input == "exit"　だとちゃんと処理できなかったがinput.equals("exit")だとしっかり処理がうごいた。
				  // scannerでifの処理をするときはequalsがいい
				  if (input.equals("exit")) {
					  System.out.println("exitが入力されました");
					  scanner.close();
					  break;
				  } else if (input.equals("task") || input.equals("create")) {
					  System.out.println("タスクを作成します");
					  try(PreparedStatement ps = conn.prepareStatement(SQL)){
						  ps.setString(1, "Remon");
						  String taskName = scanner.next();
						  System.out.println("タスク名を入力してください");
						  ps.setString(2, taskName);
						  String taskContent = scanner.next();
						  System.out.println("タスクの詳細を入力してください");
						  ps.setString(3, taskContent);
						  System.out.println("タスクを完了させる年月日を⚪⚪⚪⚪/××/△△︎の様に入力してください");
						  String taskDeadline = scanner.next();
						  ps.setString(4, taskDeadline);
						  System.out.println("タスクのカテゴリーを入力してください");
						  String taskCategory = scanner.next();
						  ps.setString(5, taskCategory);
						  ps.setString(6, "未完了");
						  LocalDateTime nowDate = LocalDateTime.now();
						  DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
						  String taskTime = dtf1.format(nowDate);
						  ps.setString(7, taskTime);
						  
						  ps.executeUpdate();
			              conn.commit();
						  System.out.println("Insert successful!");
					  } catch (Exception e) {
						  conn.rollback();
			              System.out.println("rollback");
			              throw e;
					  }
				  } else {
					  System.out.println("もう一度入力してください");
				  }
			}
			
		}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// dbのクローズ
				if (dbResult != null) {
					dbResult.close();
					System.out.println("接続終了");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
