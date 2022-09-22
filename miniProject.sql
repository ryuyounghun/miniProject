-- �ؿ� INSERT�� � ����� �ξ����ϴ�.

-- ȸ�� ���̺�
CREATE TABLE MEMBERS(
    MEMBERID VARCHAR2(10) PRIMARY KEY,
    MEMBERPW VARCHAR2(100) NOT NULL,
    MEMBERNAME VARCHAR2(30) NOT NULL,
    PHONE VARCHAR2(20) NOT NULL,
    BIRTH DATE,
    SEX VARCHAR2(20),
    ADDRESS VARCHAR2(1000),
    
    ENABLED NUMBER(1) DEFAULT 1 CHECK(ENABLED = 0 OR ENABLED = 1),
    ROLENAME VARCHAR2(10) DEFAULT 'ROLE_USER' CHECK(ROLENAME = 'ROLE_ADMIN' OR ROLENAME = 'ROLE_USER')
);
-- ��ǰ ���̺�
create table item(
    ITEMNUM NUMBER PRIMARY KEY,
    ITEMNAME VARCHAR2(30) NOT NULL,
    ITEMPRICE NUMBER(10) NOT NULL,
    ITEMCATEGORY VARCHAR2(100),
    ITEMIMAGE VARCHAR2(100),
    ITEMCONTENT VARCHAR2(1000)
    
    );
-- �ֹ����� ���̺�  
create table order_table(
    memberid varchar2(10) references members(memberid) on delete cascade NOT NULL,
    ORDERNUM NUMBER references item(itemnum) on delete cascade NOT NULL,
    ORDERNAME VARCHAR2(30) NOT NULL,
    ORDERPRICE NUMBER(10) NOT NULL,
    ORDERIMAGE VARCHAR2(100),
    QUANTITY NUMBER(3) default 1
);
-- �Խ��� ���̺�
CREATE TABLE NOTICE(
    NOTICENUM     NUMBER PRIMARY KEY,
    TITLE        VARCHAR2(500) NOT NULL,
    CONTENT      VARCHAR2(2000) NOT NULL,
    VIEWCOUNT    NUMBER DEFAULT 0,
    INPUTDATE    DATE DEFAULT SYSDATE,
    
    ORIGINALFILE VARCHAR2(200),
    SAVEDFILE VARCHAR2(200)
);

create sequence NOTICE_SEQ; -- �Խ��� ��ȣ ������
CREATE SEQUENCE ITEM_SEQ1;  -- ��ǰ ��ȣ ������

-------------------------------------------------------------------------------------------------
-- ��ǰ�� ������ ������ ���������� ��ǰ ����� �ؾ��ؼ� insert���� �̸� ����� �ξ����ϴ�.

-- �����ڰ��� ���� ������Ʈ
UPDATE MEMBERS
SET ROLENAME = 'ROLE_ADMIN'
WHERE MEMBERID = '';

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '����1', '3000', '1', '/images/burger1.png', '�׳� �ܹ���');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '����1��Ʈ', '6000', '2', '/images/burger1set.png', '�׳� �ܹ����� ��Ʈ');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '����Ƣ��', '3000', '3', '/images/fries.png', '���ִ� ����Ƣ��');

INSERT INTO ITEM(ITEMNUM, ITEMNAME, ITEMPRICE, ITEMCATEGORY, ITEMIMAGE, ITEMCONTENT)
VALUES(ITEM_SEQ1.nextval, '��ī�ݶ�', '1500', '4', '/images/coke1.png', '���ִ� �ݶ�');

commit;