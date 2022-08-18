package com.viktor.calculator.dao;

import com.viktor.calculator.domain.Expression;
import com.viktor.calculator.exceptions.DaoException;
import com.viktor.calculator.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.viktor.calculator.dao.ConnectionBuilder.getConnection;
import static com.viktor.calculator.utils.Constants.*;

public class ExpressionDaoImpl implements ExpressionDao {

    private static final Logger logger = LoggerFactory.getLogger(ExpressionDaoImpl.class);

    @Override
    public Long saveExpression(Expression expression) throws DaoException {
        long result = -1L;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(Constants.INSERT_EXPRESSION, new String[]{"id"})) {
            connection.setAutoCommit(false);
            try {
                statement.setString(1, expression.getExpression());
                statement.setDouble(2, expression.getResult());
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Expression> findAllExpressions() throws DaoException {
        List<Expression> result = new LinkedList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_EXPRESSIONS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expression expression = new Expression(resultSet.getString("title"));
                result.add(expression);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Expression> findExpressionsWhereResultEquals(Double value) throws DaoException {
        List<Expression> result = new LinkedList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EXPRESSIONS_WHERE_RESULT_EQUALS)) {
            statement.setDouble(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expression expression = new Expression(resultSet.getString("title"));
                result.add(expression);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Expression> findExpressionsWhereResultIsGreaterThan(Double value) throws DaoException {
        List<Expression> result = new LinkedList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EXPRESSIONS_WHERE_RESULT_GREATER)) {
            statement.setDouble(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expression expression = new Expression(resultSet.getString("title"));
                result.add(expression);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Expression> findExpressionsWhereResultIsLessThan(Double value) throws DaoException {
        List<Expression> result = new LinkedList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EXPRESSIONS_WHERE_RESULT_LESS)) {
            statement.setDouble(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Expression expression = new Expression(resultSet.getString("title"));
                result.add(expression);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Expression findExpressionById(Long id) throws DaoException {
        Expression result = null;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_EXPRESSION_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = new Expression(resultSet.getString("title"));
                result.setResult(resultSet.getDouble("result"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public long updateExpression(Long id, String expression) throws DaoException {
        long result = -1L;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_EXPRESSION_BY_ID, new String[]{"id"})) {
            connection.setAutoCommit(false);
            try {
                statement.setString(1, expression);
                statement.setLong(2, id);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public long updateResultOfExpression(Long id, Double resultOfExpression) throws DaoException {
        long result = -1L;
        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_RESULT_BY_UPDATED_EXPRESSION, new String[]{"id"})) {
            connection.setAutoCommit(false);
            try {
                statement.setDouble(1, resultOfExpression);
                statement.setLong(2, id);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
        return result;
    }

}
