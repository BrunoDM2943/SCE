/**
 * 
 */
package br.com.sce;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.sce.curso.Curso;
import br.com.sce.dao.DaoException;
import br.com.sce.dao.IDao;

/**
 * Classe: AOPTeste <br>
 * @author Bruno <br>
 * 
 * Propósito:  <br>
 * Data de criaçãoo: 01/05/2015 <br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@ActiveProfiles("test")
public class CursoDaoTest {
	

	@Autowired
	private IDao<Curso> genericDao;
	
	@Autowired
	private DataSource dataSource;

	@Test
	public void gravarCursoComSucesso() throws Exception{
		Curso c = new Curso();
		genericDao.salvar(c);
	}
	
	@Test(expected=DaoException.class)
	public void gravarCursoDuplicado() throws Exception{
		Curso c = new Curso();
		c.setNome("C1");
		try{
			genericDao.salvar(c);
			genericDao.salvar(c);
		}catch(Exception e){
			throw new DaoException(e);
		}
	}
	
	@Test
	public void deletarCurso() throws Exception{
		Curso c = new Curso();
		c.setNome("Teste");
		genericDao.salvar(c);
		genericDao.deletar(c);
	}

	@Test(expected = DaoException.class)
	public void deletarCursoNaoEncontrado() throws Exception{
		Curso c = null;
		try{
			genericDao.deletar(c);
		}catch(Exception e){
			throw new DaoException(e);
		}
		
	}
	
	@After
	public void after(){
		System.out.println("Deletando ");
		new JdbcTemplate(dataSource).execute("delete from Curso");
	}	

}
