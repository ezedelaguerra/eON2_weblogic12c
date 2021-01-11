--Not valid Buyers for given Seller User--
SELECT usr.username 
FROM eon_users usr 
WHERE usr.username IN ('b_ca01', 'b_ca02', 'b_ca03', 'b_cb02') 
and usr.username not in
	(
	SELECT usrs.username sn
	FROM EON_USER_DEALING_PATTERN dpsell JOIN EON_USERS usrs ON dpsell.USER_02 = usrs.USER_ID
	WHERE dpsell.user_01 = ( Select u.user_id from eon_users u WHERE u.username = 's_ca02')
	)


--Not valid Sellers for given Buyer User--
SELECT usr.username 
FROM eon_users usr 
WHERE usr.username IN ('s_ca01', 's_ca02', 's_ca03', 's_cb02') 
and usr.username not in
	(
	SELECT usrs.username sn
	FROM EON_USER_DEALING_PATTERN dpbuy JOIN EON_USERS usrs ON dpbuy.USER_01 = usrs.USER_ID
	WHERE dpbuy.user_02 = ( Select u.user_id from eon_users u WHERE u.username = 'b_ca01')
	) 

	
--Not valid Sellers/Buyers for given Admin User--
SELECT usr.username 
FROM eon_users usr 
WHERE usr.username IN ('b_ca01', 'b_ca02', 'b_ca03', 'b_cb02') 
and usr.username not in
	(
	SELECT usrs.username sn
	FROM EON_ADMIN_MEMBER dpadmin JOIN EON_USERS usrs ON dpadmin.MEMBER_ID = usrs.USER_ID
	WHERE dpadmin.ADMIN_ID = ( Select u.user_id from eon_users u WHERE u.username = 'ba_ca01') 
	)

	
--Not valid Sellers for given BuyerAdmin User and Buyer members--
SELECT usr.username 
FROM eon_users usr 
WHERE usr.username IN ('s_ca01', 's_ca02', 's_ca03', 's_cb02') 
and usr.username not in
	(
	SELECT Distinct usrs.username sn
	FROM EON_USER_DEALING_PATTERN dpsell JOIN EON_USERS usrs ON dpsell.USER_01 = usrs.USER_ID
	WHERE dpsell.user_02 in(
	        SELECT us.user_id
	        FROM EON_ADMIN_MEMBER dpadmin JOIN EON_USERS us ON dpadmin.MEMBER_ID = us.USER_ID
	        WHERE dpadmin.ADMIN_ID = ( Select u.user_id from eon_users u WHERE u.username = 'ba_ca01') 
	            AND us.username in ('b_ca02', 'b_ca03', 'b_cb02')
	        )
	)

	
--Not valid Buyers for given SellerAdmin User and Seller members--
SELECT usr.username 
FROM eon_users usr 
WHERE usr.username IN ('b_ca01', 'b_ca02', 'b_ca03', 'b_cb02') 
and usr.username not in
	(
	SELECT Distinct usrs.username sn
	FROM EON_USER_DEALING_PATTERN dpsell JOIN EON_USERS usrs ON dpsell.USER_02 = usrs.USER_ID
	WHERE dpsell.user_01 in(
	        SELECT us.user_id
	        FROM EON_ADMIN_MEMBER dpadmin JOIN EON_USERS us ON dpadmin.MEMBER_ID = us.USER_ID
	        WHERE dpadmin.ADMIN_ID = ( Select u.user_id from eon_users u WHERE u.username = 'sa_ca02') 
	            AND us.username in ('s_ca01', 's_ca03', 's_cb02')
	        )
	)




