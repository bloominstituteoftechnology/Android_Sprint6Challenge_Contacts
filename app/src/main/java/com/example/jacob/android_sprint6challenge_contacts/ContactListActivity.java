package com.example.jacob.android_sprint6challenge_contacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * An activity representing a list of Contacts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ContactDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ContactListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    public static ArrayList<Contact> contactList;
    static Context context;
    SimpleItemRecyclerViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.contact_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        final View recyclerView = findViewById(R.id.contact_list);
        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);

        AtomicBoolean canceled = new AtomicBoolean(false);
        ContactsDao.getContacts(canceled, new ContactsDao.ObjectCallback<ArrayList<Contact>>() {
            @Override
            public void returnObjects(ArrayList<Contact> contacts) {
                contactList = contacts;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        listAdapter.notifyDataSetChanged();
                        setupRecyclerView((RecyclerView) recyclerView);
                    }
                });
//                Log.i("OutputTest", contacts.toString());
            }
        });

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        listAdapter = new SimpleItemRecyclerViewAdapter(this, contactList, mTwoPane);
        recyclerView.setAdapter(listAdapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ContactListActivity mParentActivity;
        private final ArrayList<Contact> mValues;
        private final boolean mTwoPane;
        private AtomicBoolean canceledStatus;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ContactDetailFragment.ARG_ITEM_ID, String.valueOf(contact.id));
                    ContactDetailFragment fragment = new ContactDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contact_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ContactDetailActivity.class);
                    intent.putExtra(ContactDetailFragment.ARG_ITEM_ID, contact.id);
                    context.startActivity(intent);
                }
            }
        };


        SimpleItemRecyclerViewAdapter(ContactListActivity parent,
                                      ArrayList<Contact> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
            canceledStatus = new AtomicBoolean(false);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            canceledStatus.set(false);
            final String imageUrl = mValues.get(position).thumbImageUrl;
            File file = PublicFunctions.getFileFromCache(PublicFunctions.getSearchText(imageUrl), context);
            if (file == null) {
                Bitmap bitmap;
                holder.mImageView.setImageResource(R.color.colorPrimaryDark);
                ContactsDao.ObjectCallback<Boolean> callback = new ContactsDao.ObjectCallback<Boolean>() {
                    @Override
                    public void returnObjects(Boolean object) {
                        if (object) {
                            File file = PublicFunctions.getFileFromCache(PublicFunctions.getSearchText(imageUrl), context);
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyItemChanged(holder.getAdapterPosition());
                                }
                            });
                        }
                    }
                };
                ContactsDao.getImageFile(imageUrl, context, canceledStatus, callback);
            } else {
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                holder.mImageView.setImageBitmap(bitmap);
            }

            holder.mNameView.setText(mValues.get(position).name);
            holder.mIdView.setText(String.valueOf(mValues.get(position).id));
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            canceledStatus.set(true);
        }

        @Override
        public int getItemCount() {
            if (mValues == null) {
                return 0;
            } else {
                return mValues.size();
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mNameView;
            final ImageView mImageView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mNameView = view.findViewById(R.id.name);
                mImageView = view.findViewById(R.id.image);
            }
        }
    }
}