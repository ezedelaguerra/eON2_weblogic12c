insert into EON_COMPANY_DEALING_PATTERN (company_dealing_pattern_id, company_01, company_02, dealing_pattern_relation_id, isactive) VALUES (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, (select company_id from EON_COMPANY where company_name = '田村商事株式会社'), (select company_id from EON_COMPANY where company_name = 'フレッシュリミックス株式会社（京急）'), 10000, '1');

commit;