/* Since we cannot directly change the size of the data type because it requires 
 * us to empty first the table.
 * Below are the steps to create and apply the modification of 
 * the column. 
 * 
 * */

/* ENHANCEMENT START 20120726: JOVSAB - Redmine 864 */

/* Create a temporary column "mail_to" */
ALTER TABLE EON_COMMENTS_OUTBOX ADD (MAIL_TO clob);

/* Insert all the records from the RECIPIENTS_ADDRESS to the temporary column "mail_to" */
UPDATE EON_COMMENTS_OUTBOX SET MAIL_TO = RECIPIENTS_ADDRESS;

/* Change or rename the RECIPIENTS_ADDRESS */
ALTER TABLE EON_COMMENTS_OUTBOX RENAME COLUMN RECIPIENTS_ADDRESS TO RECIPIENTS_ADDRESS_OLD;

/* Change or rename the temporary table "mail_to" to RECIPIENTS_ADDRESS */
ALTER TABLE EON_COMMENTS_OUTBOX RENAME COLUMN MAIL_TO TO RECIPIENTS_ADDRESS;

/* ENHANCEMENT END 20120726: */


commit;

