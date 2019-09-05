package com.sonny.spring_tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {
	
	@Autowired
	private FooService FooServiceImpl;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollbackException();
        //throw new ArrayIndexOutOfBoundsException();不指定Exception类型时，
        //只要是RunTimeException就会回滚，指定了必须是RollbackException.class才可以
    }

    /**
     * 
     * 
     * 和@Transactional，证明了require的传播特性，插入了两条数据;
     * 
     * 
     */
    @Override
    //在insertThenRollback()下，去掉@Transactional(rollbackFor = RollbackException.class)，不会回滚，
    //因为没有spring aop生成的代理类来增强->可通过引用自己来解决
    //@Transactional(rollbackFor = RollbackException.class)
    public void invokeInsertThenRollback() throws RollbackException {
    	// 加了jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')"),
    	jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
    	//没有
        FooServiceImpl.insertThenRollback();
    }
}
