insert into EON_COMPANY_DEALING_PATTERN (company_dealing_pattern_id, company_01, company_02, dealing_pattern_relation_id, isactive) VALUES (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, (select company_id from EON_COMPANY where company_name = '�c�������������'), (select company_id from EON_COMPANY where company_name = '�t���b�V�����~�b�N�X������Ёi���}�j'), 10000, '1');

commit;