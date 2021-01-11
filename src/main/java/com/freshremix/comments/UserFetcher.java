package com.freshremix.comments;

import java.util.List;

import com.freshremix.dao.UserDao;
import com.freshremix.model.User;
import com.freshremix.service.DealingPatternService;

public interface UserFetcher {

	List<User> getUser (User user,
		UserFetcherHelper ufh,
		DealingPatternService dealingPatternService,
		UserDao usersInfoDaos);
	
}
