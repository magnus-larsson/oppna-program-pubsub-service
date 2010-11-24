package se.vgregion.pubsub.push.repository.jpa;
/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import se.vgregion.pubsub.push.PushSubscriber;
import se.vgregion.pubsub.push.UnitTestConstants;
import se.vgregion.pubsub.push.impl.DefaultPushSubscriber;
import se.vgregion.pubsub.push.repository.PushSubscriberRepository;

@ContextConfiguration({"classpath:spring/pubsub-push-jpa.xml", "classpath:spring/test-jpa.xml"})
public class JpaPushSubscriberRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private PushSubscriberRepository subscriberRepository;
    
    private DefaultPushSubscriber expected;
    
    @Before
    @Transactional
    @Rollback(false)
    public void setup() {
        subscriberRepository = applicationContext.getBean(PushSubscriberRepository.class);
        expected = new DefaultPushSubscriber(subscriberRepository, UnitTestConstants.TOPIC, UnitTestConstants.CALLBACK, 100, "verify");
        subscriberRepository.persist(expected);
    }
    
    @Test
    @Transactional
    @Rollback
    public void findByPk() {
        PushSubscriber actual = subscriberRepository.find(expected.getId());
        
        Assert.assertEquals(expected.getTopic(), actual.getTopic());
    }

}
