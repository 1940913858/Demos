package com.example.meetyou.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meetyou.myapplication.R;
import com.example.meetyou.myapplication.bean.ChatList;
import com.example.meetyou.myapplication.utils.Constant;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MyItemClickListener mItemClickListener;
    private List<ChatList> list;
    private LayoutInflater inflater;
    private Context context;
    private int variableId;

    public ChatListAdapter(Context context, List<ChatList> list, int variableId) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.variableId = variableId;
    }

    /**
     * 添加一条数据并刷新界面
     *
     * @param chatList
     */
    public void addData(ChatList chatList) {
        list.add(list.size(), chatList);
        notifyItemInserted(list.size());
    }

    /**
     * 根据不同的条目返回不同布局类型的方法
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ChatList chatList = list.get(position);
        //发送
        if (chatList.getTypes() == ChatList.Type.SendOut) {
            if (chatList.getMultipleOptions() == ChatList.Type.Text) {
                //文字
                return Constant.SendOutText;
            } else if (chatList.getMultipleOptions() == ChatList.Type.Image) {
                //图片
                return Constant.SendOutImage;
            }
            //接收
        } else if (chatList.getTypes() == ChatList.Type.Receive) {
            if (chatList.getMultipleOptions() == ChatList.Type.Text) {
                //文字
                return Constant.ReceiveText;
            } else if (chatList.getMultipleOptions() == ChatList.Type.Image) {
                //图片
                return Constant.ReceiveImage;
            }
        }
        return super.getItemViewType(position);
    }

    /**
     * 判断加载不同的item条目
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.SendOutText) {
            View sendText = LayoutInflater.from(context).inflate(R.layout.item_chat_send_out_text,
                    parent,false);

            ChatListAdapter.ViewHolderSendOutText viewHolder = new ChatListAdapter
                    .ViewHolderSendOutText(sendText,mItemClickListener);
            return viewHolder;
        }
        else if (viewType == Constant.SendOutImage) {
            View sendImage = LayoutInflater.from(context).inflate(R.layout.item_chat_send_out_image,parent,false);

            ChatListAdapter.ViewHolderSendOutImage viewHolder = new ChatListAdapter
                    .ViewHolderSendOutImage(sendImage,
                    mItemClickListener);
            return viewHolder;
        } else if (viewType == Constant.ReceiveText) {
            View receiveText = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_text,
                    parent,false);
            ChatListAdapter.ViewHolderReceiveText viewHolder = new ChatListAdapter
                    .ViewHolderReceiveText(receiveText,
                    mItemClickListener);
            return viewHolder;
        } else if (viewType == Constant.ReceiveImage) {
            View receiveImage = LayoutInflater.from(context).inflate(R.layout.item_chat_receive_image,
                    parent,false);

            ChatListAdapter.ViewHolderReceiveImage viewHolder = new ChatListAdapter
                    .ViewHolderReceiveImage(receiveImage,
                    mItemClickListener);
            return viewHolder;
        }
        return null;
    }

    /**
     * 通过不同是item加载不同的数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list == null || list.size() <= 0){
            return;
        }
        if (holder instanceof ViewHolderSendOutText) {
//            ((ViewHolderSendOutText) holder).tv_item_send_txt.setText(list.get(position).getContent());
        } else if (holder instanceof ViewHolderSendOutImage) {
            ChatList chatList = list.get(position);
            List<String> pictureFilePaths = chatList.getPictureFilePaths();

            ((ViewHolderSendOutImage) holder).iv_send_image_ll.removeAllViews();
            for (int i = 0;i<pictureFilePaths.size();i++){
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.width = 168 *3;
                lp.height = 168*3;
                imageView.setLayoutParams(lp);

                Glide
                        .with(context)
                        .load(pictureFilePaths.get(i))
                        //禁止缓存
                        .skipMemoryCache(true)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);

                ((ViewHolderSendOutImage) holder).iv_send_image_ll.addView(imageView);
            }

            Glide
                    .with(context)
                    .load(chatList.getImages())
                    //禁止缓存
                    .skipMemoryCache(true)
                    .error(R.mipmap.ic_launcher)
                    .into(((ViewHolderSendOutImage) holder).iv_send_image);
        } else if (holder instanceof ViewHolderReceiveText) {
        } else if (holder instanceof ViewHolderReceiveImage) {
//            Glide
//                    .with(context)
//                    .load(list.get(position).getImages())
//                    //禁止缓存
//                    .skipMemoryCache(true)
//                    .error(R.mipmap.ic_launcher)
//                    .into(((ViewHolderReceiveImage) holder).iv_from_image);
        }
    }

    /**
     * list总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 发送文字
     */
    public static class ViewHolderSendOutText extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private ChatListAdapter.MyItemClickListener myItemClickListener;
        private TextView tv_item_send_txt;

        public ViewHolderSendOutText(View itemView, ChatListAdapter.MyItemClickListener
                myItemClickListener) {
            super(itemView);
            this.myItemClickListener = myItemClickListener;
            tv_item_send_txt = itemView.findViewById(R.id.tv_item_send_txt);

            itemView.findViewById(R.id.from_person).setOnClickListener(this);
        }

        /**
         * 设置点击事件
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.from_person:
                        myItemClickListener.onItemChatHead(v, getPosition());
                        break;
                }
            }
        }

    }

    /**
     * 发送图片
     */
    public static class ViewHolderSendOutImage extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private ChatListAdapter.MyItemClickListener myItemClickListener;
        private ImageView iv_send_image;
        private LinearLayout iv_send_image_ll;

        public ViewHolderSendOutImage(View itemView, ChatListAdapter.MyItemClickListener
                myItemClickListener) {
            super(itemView);
            this.myItemClickListener = myItemClickListener;
            iv_send_image = (ImageView) itemView.findViewById(R.id.iv_send_image);
            itemView.findViewById(R.id.from_person).setOnClickListener(this);
            itemView.findViewById(R.id.iv_send_image).setOnClickListener(this);
            iv_send_image_ll = (LinearLayout) itemView.findViewById(R.id.iv_send_image_ll);
//            itemView.findViewById(R.id.iv_send_image_ll).setOnClickListener(this);

        }

        /**
         * 设置点击事件
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.from_person:
                        myItemClickListener.onItemChatHead(v, getPosition());
                        break;
                    case R.id.iv_send_image:
                        myItemClickListener.onItemChatImage(v, getPosition());
                        break;
                }
            }
        }
    }

    /**
     * 接收文字
     */
    public static class ViewHolderReceiveText extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private ChatListAdapter.MyItemClickListener myItemClickListener;

        public ViewHolderReceiveText(View itemView, ChatListAdapter.MyItemClickListener
                myItemClickListener) {
            super(itemView);
            this.myItemClickListener = myItemClickListener;
            itemView.findViewById(R.id.from_person).setOnClickListener(this);
        }

        /**
         * 设置点击事件
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.from_person:
                        myItemClickListener.onItemChatHead(v, getPosition());
                        break;
                }
            }
        }
    }

    /**
     * 接收图片
     */
    public static class ViewHolderReceiveImage extends RecyclerView.ViewHolder implements View
            .OnClickListener {
        private ChatListAdapter.MyItemClickListener myItemClickListener;
        private ImageView iv_from_image;

        public ViewHolderReceiveImage(View itemView, ChatListAdapter.MyItemClickListener
                myItemClickListener) {
            super(itemView);
            this.myItemClickListener = myItemClickListener;
            iv_from_image = (ImageView) itemView.findViewById(R.id.iv_from_image);
            itemView.findViewById(R.id.from_person).setOnClickListener(this);
            itemView.findViewById(R.id.iv_from_image).setOnClickListener(this);
        }

        /**
         * 设置点击事件
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (myItemClickListener != null) {
                switch (v.getId()) {
                    case R.id.from_person:
                        myItemClickListener.onItemChatHead(v, getPosition());
                        break;
                    case R.id.iv_from_image:
                        myItemClickListener.onItemChatImage(v, getPosition());
                        break;
                }
            }
        }

    }

    /**
     * 定义接口实现点击事件的回调
     */
    public interface MyItemClickListener {
        void onItemChatHead(View view, int position);

        void onItemChatVoice(View view, int position);

        void onItemChatImage(View view, int position);

        void onItemChatPlayer(View view, int position);
    }

    public void setItemClickListener(ChatListAdapter.MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }


}
