package br.com.caelum.cadastro.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDAO extends SQLiteOpenHelper{
	
	private static final int VERSAO = 1;
	private static final String TABELA = "Alunos";
	private static final String DATABASE = "CadastroAluno";

	public AlunoDAO(Context context) {
		super(context, DATABASE, null, VERSAO);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl  = "CREATE TABLE " +TABELA + " (id INTEGER PRIMARY KEY," +
				" nome TEXT UNIQUE NOT NULL, telefone TEXT, endereco TEXT," +
				" site TEXT, nota REAL, caminhoFoto TEXT);";
		
		db.execSQL(ddl);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public void insere(Aluno aluno){
		ContentValues values = new ContentValues();
		
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhoFoto", aluno.getCaminhoFoto());
		
		getWritableDatabase().insert(TABELA, null, values);
	}
	
	public List<Aluno> getList(){
		List<Aluno> alunos = new ArrayList<Aluno>();
		Cursor cursor = null;
		
		String sql = "SELECT * FROM " + TABELA + ";";
		
		try {
			cursor = getReadableDatabase().rawQuery(sql, null);
			
			while (cursor.moveToNext()) {
				Aluno aluno = new Aluno();
				
				aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
				aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
				aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
				aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
				aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
				aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
				aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
				
				alunos.add(aluno);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return alunos;
	}
	
	public void deletar(Aluno aluno){
		String[] args = {aluno.getId().toString()};
		getWritableDatabase().delete(TABELA, "id=?", args);
		
	}
	
	public void alterar(Aluno aluno){
		ContentValues values = new ContentValues();
		values.put("nome", aluno.getNome());
		values.put("telefone", aluno.getTelefone());
		values.put("endereco", aluno.getEndereco());
		values.put("site", aluno.getSite());
		values.put("nota", aluno.getNota());
		values.put("caminhoFoto", aluno.getCaminhoFoto());
		
		getWritableDatabase().update(TABELA, values, "id=?", 
				new String[]{aluno.getId().toString()});
	}
	
	public boolean isAluno(String telefone){
		String[] parametros = {telefone};
		
		Cursor rawQuery = getReadableDatabase().rawQuery("SELECT telefone from "
				+ TABELA + " WHERE telefone =?", parametros);
		
		int total = rawQuery.getCount();
		rawQuery.close();
		
		return total > 0;
	}
	
}
