/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosuit.DGomezTagleProgramacionNCapasMaven.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ALIEN62
 */
@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
        dataSource.setUrl("jdbc:oracle:thin:@LocalHost:1521:orcl");
        dataSource.setUsername("dgomeztagleprogramancapas");
        dataSource.setPassword("password1");
                
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate  jdbcTemplade(DataSource dataSource ){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    
    }
    
}
