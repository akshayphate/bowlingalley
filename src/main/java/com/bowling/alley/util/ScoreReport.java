package main.java.com.bowling.alley.util;
/**
 *
 * SMTP implementation based on code by R�al Gagnon mailto:real@rgagnon.com
 *
 */


import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;


import main.java.com.bowling.alley.model.Bowler;
import main.java.com.bowling.alley.model.Score;

public class ScoreReport
{
	private String content;

	public ScoreReport(Bowler bowler, int[] scores, int games)
	{
		String nick = bowler.getNick();
		String full = bowler.getFullName();
		Vector<Score> v = null;

		try
		{
			v = ScoreHistoryFile.getScores(nick);
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e);
		}

		content = generateContent(full, nick, scores, v);
	}

	private String generateContent(String fullName, String nickName, int[] scores, Vector<Score> v)
	{
		StringBuilder contentBuilder = new StringBuilder();

		contentBuilder.append("");
		contentBuilder.append("--Lucky Strike Bowling Alley Score Report--\n");
		contentBuilder.append("\n");
		contentBuilder.append("Report for " + fullName + ", aka \"" + nickName + "\":\n");
		contentBuilder.append("\n");
		contentBuilder.append("Final scores for this session: ");
		contentBuilder.append(scores[0]);

		for (int i = 1; i < scores.length; i++)
		{
			contentBuilder.append(", ").append(scores[i]);
		}

		contentBuilder.append(".\n\n\n");
		contentBuilder.append("Previous scores by date: \n");

		for (Score score : v)
		{
			content += "  " + score.getDate() + " - " +  score.getScore();
			content += "\n";
		}

		contentBuilder.append("\n\n");
		contentBuilder.append("Thank you for your continuing patronage.");

		return contentBuilder.toString();
	}

	public void sendEmail(String recipient)
	{

		try (
				Socket s = new Socket("osfmail.rit.edu", 25);
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), "8859_1"));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "8859_1"));
		)
		{
			// here you are supposed to send your username
			sendln(in, out, "HELO world");
			sendln(in, out, "MAIL FROM: <mda2376@rit.edu>");
			sendln(in, out, "RCPT TO: <" + recipient + ">");
			sendln(in, out, "DATA");
			sendln(out, "Subject: Bowling Score Report ");
			sendln(out, "From: <Lucky Strikes Bowling Club>");

			sendln(out, "Content-Type: text/plain; charset=\"us-ascii\"\r\n");
			sendln(out, content + "\n\n");
			sendln(out, "\r\n");

			sendln(in, out, ".");
			sendln(in, out, "QUIT");
			s.close();
		}
		catch (Exception e)
		{
			System.out.println("");
		}
	}

	public void sendPrintout()
	{
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintableText printobj = new PrintableText(content);

		job.setPrintable(printobj);

		if (job.printDialog())
		{
			try
			{
				job.print();
			}
			catch (PrinterException e)
			{
				System.out.println(e);
			}
		}

	}

	public void sendln(BufferedReader in, BufferedWriter out, String s)
	{
		try
		{
			out.write(s + "\r\n");
			out.flush();
			// System.out.println(s);
			s = in.readLine();
			// System.out.println(s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void sendln(BufferedWriter out, String s)
	{
		try
		{
			out.write(s + "\r\n");
			out.flush();
			System.out.println(s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


}
