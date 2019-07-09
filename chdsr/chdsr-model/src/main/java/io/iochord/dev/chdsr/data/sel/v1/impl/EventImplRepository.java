package io.iochord.dev.chdsr.data.sel.v1.impl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Repository
public interface EventImplRepository extends JpaRepository<EventImpl, String> {
	
}
