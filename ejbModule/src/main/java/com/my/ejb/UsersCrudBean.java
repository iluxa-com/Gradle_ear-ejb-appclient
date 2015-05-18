package com.my.ejb;

import com.my.interceptor.Interceptor;
import com.my.model.Users;
import com.my.remote.UsersCrudRemote;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.soap.SOAPBinding;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by marcin on 16.05.15.
 */

@Stateless
@Interceptors(Interceptor.class)
@TransactionManagement(TransactionManagementType.BEAN)
@Remote(UsersCrudRemote.class)
public class UsersCrudBean implements UsersCrudRemote {

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    @Resource
    SessionContext sc;

    @Override
    public void save(Users user) throws Exception{
        utx.begin();
        em.joinTransaction();
        em.persist(user);
        utx.commit();
    }

    @Override
    public void delete(Users user) throws Exception {
        utx.begin();
        em.joinTransaction();
        user = em.merge(user);
        em.remove(user);
        utx.commit();
    }

    @Override
    public void update(Users user) throws Exception{
        utx.begin();
        em.joinTransaction();
        em.merge(user);
        utx.commit();
    }

    @Override
    public Users findById(Integer id)throws Exception {
        utx.begin();
        em.joinTransaction();
        Users dto = em.find(Users.class, id);
        utx.commit();
        return dto;
    }

    @Override
    public List<Users> findByLastName(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        utx.begin();
        em.joinTransaction();
        List<Users> result = null;
            TypedQuery<Users> q = em.createNamedQuery("Users.findByLastName", Users.class);
            q.setParameter("ln", lastName);
            result = q.getResultList();;
        utx.commit();
        return result;
    }

    @Override
    @Asynchronous
    public Future<Users> findByLastNameAsynchronous(String lastName) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        utx.begin();
        em.joinTransaction();
        List<Users> result = null;
        while(!sc.wasCancelCalled()) {
            TypedQuery<Users> q = em.createNamedQuery("Users.findByLastName", Users.class);
            q.setParameter("ln", lastName);
            result = q.getResultList();;
        }

        utx.commit();
        return new AsyncResult(result);
    }

    @Override
    @Asynchronous
    public void sendMail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "poczta.o2.pl");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props);
        MimeMessage m = new MimeMessage(session);
        try {
            InternetAddress[] address = {new InternetAddress(to)};
            m.setFrom(new InternetAddress("TesterToster@o2.pl"));
            m.setRecipients(Message.RecipientType.TO, address);
            m.setSubject(subject);
            m.setSentDate(new Date());
            m.setText(body);
            Transport.send(m, "TesterToster", "TesterToster");
        } catch (MessagingException e) {
            throw new MessagingException("error");
        }
    }
}
