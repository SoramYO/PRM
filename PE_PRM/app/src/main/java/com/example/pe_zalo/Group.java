    package com.example.pe_zalo;

    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.List;

    public class Group implements Serializable {
        private long id;
        private String name;
        private List<Contact> members;

        public Group(String name, List<Contact> members) {
            this.name = name;
            this.members = new ArrayList<>(members);
        }
            public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

        public String getName() {
            return name;
        }

        public List<Contact> getMembers() {
            return members;
        }

        public int getMemberCount() {
            return members.size();
        }

    public void setMembers(List<Contact> members) {
        this.members = new ArrayList<>(members);
    }

        // Tạo tên nhóm từ tên các thành viên
        public static String generateGroupName(List<Contact> members) {
            if (members.size() == 0) {
                return "Nhóm mới";
            }

            StringBuilder nameBuilder = new StringBuilder();
            int count = 0;
            for (Contact contact : members) {
                if (count > 0) {
                    nameBuilder.append(", ");
                }

                // Lấy tên (chữ cuối cùng) từ tên đầy đủ
                String[] nameParts = contact.getName().split(" ");
                nameBuilder.append(nameParts[nameParts.length - 1]);

                count++;
                // Chỉ hiển thị tối đa 3 tên
                if (count >= 3) {
                    if (members.size() > 3) {
                        nameBuilder.append(" và ").append(members.size() - 3).append(" người khác");
                    }
                    break;
                }
            }

            return nameBuilder.toString();
        }
    }